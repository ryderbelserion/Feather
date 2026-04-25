package com.ryderbelserion.feather.core.plugin.github.v2.objects

import com.ryderbelserion.feather.core.git.objects.GitUser
import com.ryderbelserion.feather.core.git.objects.commit.GitAuthor
import com.ryderbelserion.feather.core.git.objects.commit.GitCommit
import com.ryderbelserion.feather.core.git.objects.commit.tree.GitTree
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitOrigin(
    @SerialName("commit") val commit: GitCommit = GitCommit("N/A", GitAuthor("N/A", "N/A", "N/A"), GitTree("N/A")),

    @SerialName("author") val user: GitUser = GitUser("N/A", 0),

    @SerialName("stats") val stats: GitStat = GitStat(0, 0, 0),

    @SerialName("html_url") val url: String = "N/A"
)