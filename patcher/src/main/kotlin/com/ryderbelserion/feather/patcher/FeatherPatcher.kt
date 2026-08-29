package com.ryderbelserion.feather.patcher

import org.gradle.api.Project
import java.nio.file.Path

abstract class FeatherPatcher(project: Project) {

    var workingDirectory: Path = project.rootDir.toPath()

    var group = "patcher"

}