package com.ryderbelserion.feather.plugin.data

import kotlinx.serialization.Serializable

@Serializable
data class CommitData(val items: List<CommitAuthor>)