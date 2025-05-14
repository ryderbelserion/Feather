package com.ryderbelserion.feather.webhook

import com.ryderbelserion.feather.webhook.builders.MessageBuilder
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.append
import io.ktor.http.isSuccess
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
        val url = extension.get()

        val isInvalid = url.isEmpty() || !url.startsWith("https://discord.com")

        if (isInvalid) {
            println("[Feather] No valid url was specified for the discord webhook, Please check what you entered.")

            return
        }

        if (extension.username().isEmpty()) {
            println("[Feather] The username field cannot be empty! Please use webhook#username(\"insert_username\")")

            return
        }

        runBlocking(Dispatchers.IO) {
            val client = client.post(extension.get()) {
                headers {
                    append(HttpHeaders.ContentType, ContentType.Application.Json)
                }

                val content = extension.build()

                setBody(content)
            }

            val status = client.status

            if (!status.isSuccess()) {
                listOf(
                    "=== === === === ===",
                    "Response Time: ${client.responseTime}",
                    "Request Time: ${client.requestTime}",
                    "=== === === === ===",
                    "Status: ${status.value}",
                    "Description: ${status.description}",
                    "=== === === === ==="
                ).forEach { println("[Feather] $it") }
            }
        }
    }
}