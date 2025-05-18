package com.ryderbelserion.feather.core.plugin.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
@SerialName("item")
data class CommitAuthor(@SerialName("login") val author: String, @Transient val email: String = "N/A", @SerialName("avatar_url") val avatar: String = "N/A")