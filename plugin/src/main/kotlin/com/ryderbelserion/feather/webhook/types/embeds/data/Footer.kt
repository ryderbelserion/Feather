package com.ryderbelserion.feather.webhook.types.embeds.data

import com.ryderbelserion.feather.webhook.types.components.Image
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Footer(val text: String?, @SerialName("icon_url") val image: Image?)