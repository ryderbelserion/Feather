package com.ryderbelserion.feather.core.plugin

import com.ryderbelserion.feather.core.discord.DiscordExtension
import com.ryderbelserion.feather.core.plugin.utils.Git
import org.gradle.api.Project
import java.nio.file.Path

abstract class FeatherCore(project: Project) {

    var discord: DiscordExtension = project.extensions.create("discord", DiscordExtension::class.java)

    var rootDirectory: Path? = Path.of(System.getProperty("user.dir"))

    fun getGit(): Git = Git(rootDirectory)
}