package com.ryderbelserion.feather.webhook

import com.ryderbelserion.feather.webhook.builders.MessageBuilder
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.append
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

abstract class Webhook : DefaultTask() {

    @get:Input
    lateinit var extension: MessageBuilder

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    @TaskAction
    fun webhook() {
        runBlocking(Dispatchers.IO) {
            val response = client.post(System.getenv("BUILD_URL")) {
                headers {
                    append(HttpHeaders.ContentType, ContentType.Application.Json)
                }

                val content = extension.build()

                println(content)

                setBody(content)
            }

            println("Webhook Result: ${response.bodyAsText()}")
        }
    }
}