package com.ryderbelserion.feather.core.utils

import org.tinylog.kotlin.Logger

fun String.warn() {
    Logger.warn(this)
}

fun String.error() {
    Logger.error(this)
}

fun String.info() {
    Logger.info(this)
}