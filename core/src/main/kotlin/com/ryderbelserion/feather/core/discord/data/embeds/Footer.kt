package com.ryderbelserion.feather.core.discord.data.embeds

import com.ryderbelserion.feather.core.discord.data.components.Image
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Footer(val text: String?, @SerialName("icon_url") val image: Image?)