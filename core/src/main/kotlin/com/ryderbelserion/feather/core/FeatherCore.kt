package com.ryderbelserion.feather.core

import com.ryderbelserion.feather.core.modules.webhooks.DiscordExtension
import com.ryderbelserion.feather.core.api.git.builders.GitBuilder
import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import java.nio.file.Path

abstract class FeatherCore(project: Project) {

    var targetDirectory: Path = project.projectDir.toPath()

    var discord: DiscordExtension = project.extensions.create("discord", DiscordExtension::class.java)

    val builder: GitBuilder = GitBuilder(this.targetDirectory)

}