package com.ryderbelserion.feather.webhook.builders

import com.ryderbelserion.feather.webhook.types.Message
import com.ryderbelserion.feather.webhook.types.embeds.Embed

abstract class MessageBuilder {

    private var url: String = ""

    private var content: String = ""
    private var username: String = ""
    private var avatar: String = ""
    private val embeds: MutableList<Embed> = mutableListOf()

    fun post(url: String) {
        this.url = url
    }

    fun get(): String {
        return url
    }

    fun content(content: String) {
        this.content = content
    }

    fun username(username: String) {
        this.username = username
    }

    fun avatar(avatar: String) {
        this.avatar = avatar
    }

    fun embeds(builder: EmbedsBuilder.() -> Unit) {
        this.embeds.addAll(EmbedsBuilder().apply(builder).build())
    }

    internal fun build(): Message {
        return Message(
            this.content,
            this.username,
            this.avatar,
            this.embeds.toList()
        )
    }
}