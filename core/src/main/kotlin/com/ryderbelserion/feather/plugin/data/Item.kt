package com.ryderbelserion.feather.plugin.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Item(@SerialName("login") val author: String, @SerialName("avatar_url") val avatar: String)