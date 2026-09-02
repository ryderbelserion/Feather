package com.ryderbelserion.feather.patcher

import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty

abstract class FeatherPatcher(project: Project) {

    // working directory i.e. root of the project.
    abstract val workingDirectory: DirectoryProperty

    // patches directory i.e. where patches are held.
    abstract val patchesDirectory: DirectoryProperty

    // target directory i.e. where upstream is cloned before patches are applied.
    abstract val targetDirectory: DirectoryProperty

    // git utils
    // val git: Git = Git(this.targetDirectory)

    // git repo to clone
    var targetProject = ""

    // upstream branch from the project forking.
    var upstreamBranch = "upstream"

    // git branch of our project.
    var targetBranch = "main"

    // git commit hash
    var commitHash = ""

    // gradle task group
    var group = "feather"

}