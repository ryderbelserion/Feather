package com.ryderbelserion

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
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.File
import java.util.concurrent.TimeUnit

fun main() {
    val path = System.getProperty("user.dir")

    val file = File(path)

    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            })
        }
    }

    runCatching {
        val email = command(file, "git", listOf("--no-pager", "show", "-s", "--format=%ae"))

        runBlocking(Dispatchers.IO) {
            val response = client.get("https://api.github.com/search/users?q=$email") {
                headers {
                    append(HttpHeaders.ContentType, ContentType.Application.Json)
                }
            }.body<Github>()

            val item = response.items[0]

            println(item.login)
            println(item.avatar)
        }
    }.onFailure {
        println("[Feather] Failure! ${it.message}")
    }.onSuccess {
        println("[Feather] Success!")
    }
}

@Serializable
data class Github(val items: List<Items>)

@Serializable
data class Items(val login: String, @SerialName("avatar_url") val avatar: String)

fun command(file: File, command: String, arguments: List<String> = listOf()): String {
    val splitter = listOf(command) + arguments

    val process = ProcessBuilder().directory(file).command(splitter).start()

    process.waitFor(10, TimeUnit.SECONDS)

    return process.inputStream.bufferedReader().use(BufferedReader::readText).trim()
}