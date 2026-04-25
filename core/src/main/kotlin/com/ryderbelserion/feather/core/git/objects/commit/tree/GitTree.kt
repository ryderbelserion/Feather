package com.ryderbelserion.feather.core.git.objects.commit.tree

import kotlinx.serialization.Serializable

@Serializable
data class GitTree(val sha: String)