package com.ryderbelserion.feather.core.modules.webhooks.builders

import com.ryderbelserion.feather.core.modules.webhooks.builders.embeds.MultiEmbedBuilder
import com.ryderbelserion.feather.core.modules.webhooks.data.Message
import com.ryderbelserion.feather.core.modules.webhooks.data.embeds.Embed

open class MessageBuilder {

    private var url: String = ""
    private var task: String = ""
    private var group: String = "N/A"

    private var content: String = ""
    private var username: String = ""
    private var avatar: String = ""
    private val embeds: MutableList<Embed> = mutableListOf()

    fun post(url: String) {
        this.url = url
    }

    fun get(): String {
        return this.url
    }

    fun group(group: String) {
        this.group = group
    }

    fun group(): String {
        return this.group
    }

    fun task(task: String) {
        this.task = task
    }

    fun task(): String {
        return this.task
    }

    fun content(content: String) {
        this.content = content
    }

    fun username(username: String) {
        this.username = username
    }

    fun username(): String {
        return this.username
    }

    fun avatar(avatar: String) {
        this.avatar = avatar
    }

    fun embeds(builder: MultiEmbedBuilder.() -> Unit) {
        this.embeds.addAll(MultiEmbedBuilder().apply(builder).build())
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