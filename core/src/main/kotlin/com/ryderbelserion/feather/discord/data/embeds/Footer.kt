package com.ryderbelserion.feather.discord.data.embeds

import com.ryderbelserion.feather.discord.data.components.Image
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Footer(val text: String?, @SerialName("icon_url") val image: Image?)