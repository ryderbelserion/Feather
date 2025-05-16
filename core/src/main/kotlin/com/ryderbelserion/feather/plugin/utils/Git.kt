package com.ryderbelserion.feather.plugin.utils

import com.ryderbelserion.feather.plugin.data.Github
import com.ryderbelserion.feather.plugin.data.Item
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

    fun getCurrentBranch(): String = git(listOf("rev-parse", "--abbrev-ref", "HEAD"))

    fun getLatestCommitMessage(): String = git(listOf("log", "-1", "--pretty=%B"))

    fun getCurrentCommitId(): String = git(listOf("rev-parse", "--verify", "HEAD"))

    fun getCommitAuthorName(): String = git(listOf("--no-pager", "show", "-s", "--format=%an"))

    fun getCommitAuthorEmail(): String = git(listOf("--no-pager", "show", "-s", "--format=%ae"))

    fun getGithubInformation(avatar: String = "N/A"): Item {
        val email = getCommitAuthorEmail()

        var item = Item(getCommitAuthorName(), avatar)

        runCatching {
            runBlocking(Dispatchers.IO) {
                val response = client.get("https://api.github.com/search/users?q=$email") {
                    headers {
                        append(HttpHeaders.ContentType, ContentType.Application.Json)
                    }
                }.body<Github>()

                val key = response.items.firstOrNull()

                if (key != null) {
                    item = key
                }
            }
        }.onFailure {
            listOf(
                "Failed to fetch from Github API, Supplying default values...",
                it.message
            ).forEach { msg -> println("[Feather] $msg") }
        }

        return item
    }

    fun git(arguments: List<String>): String = command("git", arguments)

    /**
     * Runs a command using ProcessBuilder
     */
    fun command(command: String, arguments: List<String> = listOf()): String {
        val splitter = listOf(command) + arguments

        val process = ProcessBuilder().directory(this.directory?.toFile()).command(splitter).start()

        process.waitFor(10, TimeUnit.SECONDS)

        return process.inputStream.bufferedReader().use(BufferedReader::readText).trim()
    }
}