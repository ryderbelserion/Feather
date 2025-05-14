package com.ryderbelserion.feather.webhook.types

import com.ryderbelserion.feather.webhook.types.embeds.Embed
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val content: String?,
    val username: String?,
    @SerialName("avatar_url") val url: String,
    val embeds: List<Embed>?,
)