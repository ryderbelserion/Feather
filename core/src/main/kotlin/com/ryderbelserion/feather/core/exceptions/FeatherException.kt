package com.ryderbelserion.feather.core.exceptions

data class FeatherException(val content: String, val placeholder: String = "") : Exception(
    if (placeholder.isBlank()) {
        String.format(content, placeholder)
    } else {
        content
    }
)