package com.ryderbelserion.feather.core.plugin.utils

import com.ryderbelserion.feather.core.exceptions.FeatherException
import com.ryderbelserion.feather.core.plugin.data.CommitAuthor
import com.ryderbelserion.feather.core.plugin.data.CommitData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.append
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.nio.file.Path
import java.util.concurrent.TimeUnit

class Git(val directory: Path?) {

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            })
        }
    }

    fun getCurrentCommitAuthorName(): String = git(listOf("--no-pager", "show", "-s", "--format=%an"))

    fun getCurrentCommitAuthorEmail(): String = git(listOf("--no-pager", "show", "-s", "--format=%ae"))

    fun getCurrentCommitAuthorData(avatar: String = "N/A"): CommitAuthor {
        var data = getCurrentCommitAuthor().copy(avatar = avatar)

        runCatching {
            runBlocking(Dispatchers.IO) {
                val response = client.get("https://api.github.com/search/users?q=${data.email}") {
                    headers {
                        append(HttpHeaders.ContentType, ContentType.Application.Json)
                    }
                }.body<CommitData>()

                val key = response.items.firstOrNull()

                if (key != null) {
                    data = key.copy(email = data.email)
                }
            }
        }.onFailure {
            listOf(
                "Failed to fetch from Github API, Supplying default values...",
                it.message
            ).forEach { msg -> println("[Feather] $msg") }
        }

        return data
    }

    fun getCurrentCommitAuthor(): CommitAuthor =
        CommitAuthor(getCurrentCommitAuthorName(), getCurrentCommitAuthorEmail())

    fun getCurrentCommitHash(): String = git(listOf("rev-parse", "--verify", "HEAD"))

    fun getCurrentBranch(): String = git(listOf("rev-parse", "--abbrev-ref", "HEAD"))

    fun getCurrentCommit(): String = git(listOf("log", "-1", "--pretty=%B"))

    fun git(arguments: List<String>): String = command("git", arguments)

    /**
     * Runs a command using ProcessBuilder
     */
    private fun command(command: String, arguments: List<String> = listOf()): String {
        val splitter = listOf(command) + arguments

        val process = ProcessBuilder().directory(this.directory?.toFile()).command(splitter).start()

        process.waitFor(10, TimeUnit.SECONDS)

        return process.retrieveOutput()
    }

    /**
     * Retrieve the output of the process
     */
    private fun Process.retrieveOutput(): String {
        val output = inputStream.bufferedReader().use(BufferedReader::readText)

        val exitCode = exitValue()

        if (exitCode != 0) {
            val text = errorStream.bufferedReader().use(BufferedReader::readText)

            throw FeatherException("Failed to execute git command with code: %s", text)
        }

        return output.trim()
    }
}