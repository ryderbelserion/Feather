package com.ryderbelserion.feather.webhook.types.embeds

import com.ryderbelserion.feather.webhook.types.components.Image
import com.ryderbelserion.feather.webhook.types.embeds.data.Author
import com.ryderbelserion.feather.webhook.types.embeds.data.Footer
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