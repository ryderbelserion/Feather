package com.ryderbelserion.feather.core

import com.ryderbelserion.feather.core.modules.webhooks.DiscordExtension
import com.ryderbelserion.feather.core.util.git.GitBuilder
import org.gradle.api.Project
import java.nio.file.Path

abstract class FeatherCore(project: Project) {

    var discord: DiscordExtension = project.extensions.create("discord", DiscordExtension::class.java)

    var workingDirectory: Path = project.rootDir.toPath()

    fun getBuilder(): GitBuilder = GitBuilder(this.workingDirectory)

}