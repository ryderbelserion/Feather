package com.ryderbelserion.feather.core.plugin.github.v2.objects

import kotlinx.serialization.Serializable

@Serializable
data class GitStat(
    val total: Int,
    val additions: Int,
    val deletions: Int
)