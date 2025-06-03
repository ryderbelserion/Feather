package com.ryderbelserion.feather.core.plugin.utils

import com.ryderbelserion.feather.core.exceptions.FeatherException
import com.ryderbelserion.feather.core.plugin.data.CommitAuthor
import com.ryderbelserion.feather.core.plugin.github.GithubCommit
import com.ryderbelserion.feather.core.plugin.github.user.GithubUser
import com.ryderbelserion.feather.core.plugin.github.user.GithubUserMinimal
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

    fun getCurrentCommitAuthor(): CommitAuthor = CommitAuthor(getCurrentCommitAuthorName(), getCurrentCommitAuthorEmail())

    fun getCurrentCommitAuthorName(): String = git(listOf("--no-pager", "show", "-s", "--format=%an"))

    fun getCurrentCommitAuthorEmail(): String = git(listOf("--no-pager", "show", "-s", "--format=%ae"))

    fun getCurrentCommitHash(): String = git(listOf("rev-parse", "--verify", "HEAD"))

    fun getCurrentBranch(): String = git(listOf("rev-parse", "--abbrev-ref", "HEAD"))

    fun getCurrentCommit(): String = git(listOf("log", "-1", "--pretty=%B"))

    fun getGithubCommit(repository: String): GithubCommit {
        var commit = GithubCommit("N/A", GithubUser(-1, "N/A"))

        val hash = getCurrentCommitHash()

        runCatching {
            var response: GithubCommit?

            runBlocking(Dispatchers.IO) {
                response = client.get("https://api.github.com/repos/$repository/commits/$hash") {
                    headers {
                        append(HttpHeaders.ContentType, ContentType.Application.Json)

                    }
                }.body<GithubCommit>()

                val user = client.get("https://api.github.com/user/${response.user.id}") {
                    headers {
                        append(HttpHeaders.ContentType, ContentType.Application.Json)
                    }
                }.body<GithubUserMinimal>()

                commit = response

                commit.user.setName(user.name)
            }
        }.onFailure {
            println("Failed to retrieve data from $repository using $hash, ${it.message}")
        }

        return commit
    }

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