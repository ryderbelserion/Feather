package com.ryderbelserion.feather.core.git

import com.ryderbelserion.feather.core.plugin.github.v2.objects.GitOrigin
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import java.nio.file.Path

class GitBuilder(private val workingDirectory: Path) {

    private val utils: GitUtil = GitUtil(this.workingDirectory)

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            })
        }
    }

    fun getNewestCommit(organization: String, repository: String, hash: String): GitOrigin? {
        val response = getGitResponse(organization, repository, hash) ?: return null

        val status = response.status

        if (status == HttpStatusCode.BadGateway) {
            return null
        }

        if (status == HttpStatusCode.TooManyRequests) {
            return null
        }

        if (status == HttpStatusCode.UnprocessableEntity) {
            return null
        }

        var origin = GitOrigin()

        runCatching {
            runBlocking(Dispatchers.IO) {
                origin = response.body<GitOrigin>()
            }
        }.onFailure {
            println("Failed to serialize data from $repository!, ${it.message}")

            origin = GitOrigin()
        }

        return origin
    }

    fun getGitResponse(organization: String, repository: String, hash: String): HttpResponse? {
        var response: HttpResponse? = null

        runCatching {
            response = runBlocking(Dispatchers.IO) {
                client.get("https://api.github.com/repos/$organization/$repository/commits/$hash")
            }
        }.onFailure {
            println("Failed to retrieve data from $repository using $hash, ${it.message}")

            response = null
        }

        return response
    }

    fun getGitUser(user: String): HttpResponse? {
        var response: HttpResponse? = null

        runCatching {
            response = runBlocking {
                client.get("https://api.github.com/user/$user")
            }
        }.onFailure {
            println("Failed to retrieve data for the user $user, ${it.message}")
        }

        return response
    }
}