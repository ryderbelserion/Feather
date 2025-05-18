package com.ryderbelserion.feather.core.discord.data

import com.ryderbelserion.feather.core.discord.data.embeds.Embed
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val content: String?,
    val username: String?,
    @SerialName("avatar_url") val url: String,
    val embeds: List<Embed>?,
)