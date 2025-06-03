package com.ryderbelserion.feather.core.plugin.github.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// This shares `id` and `avatar_url` with the `author` object from api.github.com/repos/<organization/user>/<repository>/releases/<latest/tag> with api.github.com/user/<id/name> and api.github.com/repos/<organization/user>/<repository>/commits/<hash>
@Serializable
data class GithubUser(@SerialName("id") val id: Int, @SerialName("avatar_url") val avatar: String) {

    private var name: String = ""

    fun setName(name: String) {
        this.name = name
    }

    fun getName(): String = name

}