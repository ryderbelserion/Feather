package com.ryderbelserion.feather.patcher.api

data class FeatherException(val content: String, val placeholder: String = "") : Exception(
    if (placeholder.isBlank()) {
        String.format(content, placeholder)
    } else {
        content
    }
)