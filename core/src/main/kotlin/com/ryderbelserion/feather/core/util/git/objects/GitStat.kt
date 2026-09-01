package com.ryderbelserion.feather.core.util.git.objects

import kotlinx.serialization.Serializable

@Serializable
data class GitStat(
    val total: Int,
    val additions: Int,
    val deletions: Int
)