package com.ryderbelserion.feather.core.utils

import org.tinylog.kotlin.Logger

fun String.warn() {
    Logger.warn("[Feather] $this")
}

fun String.error() {
    Logger.error("[Feather] $this")
}

fun String.info() {
    Logger.info("[Feather] $this")
}