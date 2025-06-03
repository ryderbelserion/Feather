package com.ryderbelserion.feather.core.plugin.data

import kotlinx.serialization.Serializable

@Serializable
data class CommitAuthor(val author: String, val email: String = "N/A")