package com.ryderbelserion.feather.patcher

import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input

abstract class FeatherPatcher(project: Project) {

    // working directory i.e. root of the project.
    abstract val workingDirectory: DirectoryProperty

    // patches directory i.e. where patches are held.
    abstract val patchesDirectory: DirectoryProperty

    // target directory i.e. where upstream is cloned before patches are applied.
    abstract val targetDirectory: DirectoryProperty

    // the repo url
    abstract val url: Property<String>

    // the repo hash
    abstract val sha: Property<String>

    // git utils
    // val git: Git = Git(this.targetDirectory)

    // upstream branch from the project forking.
    //var upstreamBranch = "upstream"

    // git branch of our project.
    //var targetBranch = "main"

    // gradle task group
    var group = "feather"

}