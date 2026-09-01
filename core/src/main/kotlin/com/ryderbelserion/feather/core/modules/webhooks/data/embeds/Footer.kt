package com.ryderbelserion.feather.core.modules.webhooks.data.embeds

import com.ryderbelserion.feather.core.modules.webhooks.data.components.Image
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Footer(val text: String?, @SerialName("icon_url") val image: Image?)