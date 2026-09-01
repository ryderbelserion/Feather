package com.ryderbelserion.feather.core.modules.webhooks

import com.ryderbelserion.feather.core.modules.webhooks.builders.MessageBuilder

abstract class DiscordExtension {

    val webhooks = mutableMapOf<String, MessageBuilder>()

    fun webhook(configure: MessageBuilder.() -> Unit) {
        val builder = MessageBuilder().apply(configure)

        this.webhooks[builder.task()] = builder
    }
}