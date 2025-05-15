package com.ryderbelserion.feather.discord

import com.ryderbelserion.feather.discord.builders.MessageBuilder

abstract class DiscordExtension {

    val webhooks = mutableMapOf<String, MessageBuilder>()

    fun webhook(configure: MessageBuilder.() -> Unit) {
        val builder = MessageBuilder().apply(configure)

        this.webhooks.put(builder.task(), builder)
    }
}