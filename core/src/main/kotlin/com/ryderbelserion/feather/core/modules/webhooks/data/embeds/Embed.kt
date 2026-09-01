package com.ryderbelserion.feather.core.modules.webhooks.data.embeds

import com.ryderbelserion.feather.core.modules.webhooks.data.components.Image
import kotlinx.serialization.Serializable

@Serializable
data class Embed(
    val title: String?,
    val description: String?,
    val color: Int?,
    val image: Image?,
    val thumbnail: Image?,
    val author: Author?,
    val footer: Footer?,
    val fields: List<Field>?
)