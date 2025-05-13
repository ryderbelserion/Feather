package com.ryderbelserion

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
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.time.Instant

@Serializable
data class WebhookTask(val content: String, val username: String, @SerialName("avatar_url") val url: String, val embeds: List<Embed>)

@Serializable
data class Embed(val author: Author, val title: String, val color: Int, val description: String, val footer: Footer, val timestamp: String, val image: Image, val thumbnail: Image, val fields: List<Field>)

@Serializable
data class Field(val name: String, val value: String, val inline: Boolean = false)

@Serializable
data class Author(val name: String, @SerialName("icon_url") val url: String)

@Serializable
data class Footer(val text: String, @SerialName("icon_url") val url: String)

@Serializable
data class Image(val url: String)

fun main() {
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
            })
        }
    }

    val timestamp = Instant.ofEpochSecond(1747036325L).toString()

    val json = Json {
        prettyPrint = true
    }

    val url = "https://cdn.discordapp.com/avatars/209853986646261762/960565b2e3e2ccd501cbef7a4b1adcab.png"

    val embed = json.encodeToString(
        WebhookTask(
            "Clanka is our word, but you can say Clanker.", "Beidou Xi", url,
            listOf(
                Embed(
                    Author("Ryder Belserion", url), "This is a title",
                    "#628ebd".replace("#", "").toInt(16),
                    "This is a description", Footer("This is a footer", url), timestamp,
                    Image(url), Image(url), listOf(
                        Field("Title of a field", "This is a field", inline = true),
                        Field("This is another field x2", "Woooooo inline field x2", inline = true),
                        Field("This is another field", "Woooooo inline field", inline = false)
                    )
                )
            )
        )
    )

    println(embed)

    runBlocking {
        val response =
            client.post("") {
                headers {
                    append(HttpHeaders.ContentType, ContentType.Application.Json)
                }

                setBody(embed)
            }

        println("Webhook Result: ${response.bodyAsText()}")
    }
}