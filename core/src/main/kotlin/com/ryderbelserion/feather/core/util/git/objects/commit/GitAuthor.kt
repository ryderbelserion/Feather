package com.ryderbelserion.feather.core.util.git.objects.commit

import kotlinx.serialization.Serializable

@Serializable
data class GitAuthor(val name: String, val email: String, val date: String)