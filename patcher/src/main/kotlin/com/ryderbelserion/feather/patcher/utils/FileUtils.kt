package com.ryderbelserion.feather.patcher.utils

import org.gradle.api.file.DirectoryProperty
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Collectors
import kotlin.io.path.exists
import kotlin.io.path.isRegularFile
import kotlin.use

fun Path.matching(glob: String = "*"): List<Path> {
    if (!exists()) {
        return emptyList()
    }

    val matcher = fileSystem.getPathMatcher("glob:$glob")

    return Files.walk(this).use { stream ->
        stream.filter {
            it.isRegularFile() && matcher.matches(it.fileName)
        }.sorted().collect(Collectors.toList())
    }
}

fun DirectoryProperty.createDirectory() {
    asFile.get().createDirectory()
}

fun File.createDirectory() {
    mkdirs()
}

fun DirectoryProperty.toPath(): Path {
    return asFile.get().toPath()
}