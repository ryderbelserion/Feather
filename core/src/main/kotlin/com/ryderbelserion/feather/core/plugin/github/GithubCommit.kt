package com.ryderbelserion.feather.core.plugin.github

import com.ryderbelserion.feather.core.plugin.github.user.GithubUser
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubCommit(@SerialName("sha") val hash: String, @SerialName("author") val user: GithubUser)