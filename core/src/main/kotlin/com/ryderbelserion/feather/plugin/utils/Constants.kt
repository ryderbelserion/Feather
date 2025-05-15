package com.ryderbelserion.feather.plugin.utils

import java.awt.Color

fun Color.toInt(): Int {
    val red = red shl 16 and 0xFF0000
    val green = green shl 8 and 0x00FF00
    val blue = blue and 0x0000FF

    return red or green or blue
}