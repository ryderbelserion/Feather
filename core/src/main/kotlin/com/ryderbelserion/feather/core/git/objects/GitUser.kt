package com.ryderbelserion.feather.core.git.objects

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitUser(@SerialName("login") val name: String, val id: Int) {

    fun getProfile(): String = "https://github.com/$name"

    fun getAvatar(): String = "${getProfile()}.png"

}