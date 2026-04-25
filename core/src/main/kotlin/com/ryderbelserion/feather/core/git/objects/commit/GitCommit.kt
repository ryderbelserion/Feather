package com.ryderbelserion.feather.core.git.objects.commit

import com.ryderbelserion.feather.core.git.objects.commit.tree.GitTree
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitCommit(
    @SerialName("message") val message: String,

    @SerialName("author") val author: GitAuthor,

    @SerialName("tree") val tree: GitTree
)