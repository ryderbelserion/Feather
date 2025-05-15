package com.ryderbelserion.feather.discord.tasks

import com.ryderbelserion.feather.discord.DiscordExtension
import com.ryderbelserion.feather.discord.builders.MessageBuilder
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
    lateinit var task: String

    @get:Input
    lateinit var extension: DiscordExtension

    @get:Input
    var taskGroup: String = "N/A"

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    @TaskAction
    fun execute() {
        if (task.isEmpty()) {
            println("[Feather] Task name cannot be empty!")

            return
        }

        val extension: MessageBuilder? = this.project.extensions.getByType(DiscordExtension::class.java).webhooks[this.task]

        if (extension == null) {
            println("[Feather] Extension cannot be null!")

            return
        }

        if (extension.username().isEmpty()) {
            println("[Feather] The username field cannot be empty! Please use webhook#username(\"insert_username\") for the task named $task")

            return
        }

        val url = extension.get()

        val isInvalid = url.isEmpty() || !url.startsWith("https://discord.com")

        if (isInvalid) {
            println("[Feather] No valid url was specified for the discord webhook named $task, Please check what you entered.")

            return
        }

        runBlocking(Dispatchers.IO) {
            val client = client.post(url) {
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
                    "Task Name: $task",
                    "Group Name: $taskGroup",
                    "=== === === === ==="
                ).forEach { println("[Feather] $it") }
            }
        }
    }
}