package com.ryderbelserion.feather.webhook.builders

import com.ryderbelserion.feather.webhook.types.embeds.Embed

class EmbedsBuilder {

    private val embeds: MutableList<Embed> = mutableListOf()

    fun embed(builder: EmbedBuilder.() -> Unit) {
        this.embeds.add(EmbedBuilder().apply(builder).build())
    }

    internal fun build() = this.embeds.toList()
}