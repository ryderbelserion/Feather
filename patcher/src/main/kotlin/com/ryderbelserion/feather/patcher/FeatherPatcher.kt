package com.ryderbelserion.feather.patcher

import com.ryderbelserion.feather.patcher.utils.GitUtil
import org.gradle.api.Project
import java.nio.file.Path

abstract class FeatherPatcher(project: Project) {

    // working directory
    var workingDirectory: Path = project.rootDir.toPath()

    // patches directory
    var patchesDirectory: Path = workingDirectory.resolve("patches")

    // source directory i.e. where upstream patches are applied.
    var targetDirectory: Path = workingDirectory.resolve("target")

    // git utils
    val utils: GitUtil = GitUtil()

    // git repo to clone
    var targetProject = ""

    // git branch to clone
    var targetBranch = "main"

    // git commit hash
    var commitHash = ""

    // gradle task group
    var group = "feather"

}