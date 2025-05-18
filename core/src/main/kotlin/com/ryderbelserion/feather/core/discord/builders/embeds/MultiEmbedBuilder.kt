package com.ryderbelserion.feather.core.discord.builders.embeds

import com.ryderbelserion.feather.core.discord.data.embeds.Embed

class MultiEmbedBuilder {

    private val embeds: MutableList<Embed> = mutableListOf()

    fun embed(builder: EmbedBuilder.() -> Unit) {
        this.embeds.add(EmbedBuilder().apply(builder).build())
    }

    internal fun build() = this.embeds.toList()

}