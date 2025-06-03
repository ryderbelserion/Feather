package com.ryderbelserion.feather.core.plugin.github.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubUserMinimal(@SerialName("name") val name: String)