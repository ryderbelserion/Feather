package com.ryderbelserion.feather.core.modules.webhooks.data.embeds

import kotlinx.serialization.Serializable

@Serializable
data class Field(val name: String?, val value: String?, val inline: Boolean = false)