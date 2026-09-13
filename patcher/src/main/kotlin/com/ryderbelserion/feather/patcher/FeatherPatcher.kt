package com.ryderbelserion.feather.patcher

import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property

abstract class FeatherPatcher(project: Project) {

    // working directory i.e. root of the project.
    abstract val workingDirectory: DirectoryProperty

    // patches directory i.e. where patches are held.
    abstract val patchesDirectory: DirectoryProperty

    // the workspace, or target directory, a clone of the upstream with patches applied.
    abstract val targetDirectory: DirectoryProperty

    // the repo url
    abstract val url: Property<String>

    // the repo hash
    abstract val sha: Property<String>

    // gradle task group
    var group = "feather"

}