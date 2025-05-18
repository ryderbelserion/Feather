package com.ryderbelserion.feather.core.discord.data.embeds

import kotlinx.serialization.Serializable

@Serializable
data class Field(val name: String?, val value: String?, val inline: Boolean = false)