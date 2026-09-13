package com.ryderbelserion.feather.core.api.git.objects.commit.tree

import kotlinx.serialization.Serializable

@Serializable
data class GitTree(val sha: String)