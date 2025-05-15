package com.ryderbelserion.feather.plugin

import com.ryderbelserion.feather.discord.DiscordExtension
import org.gradle.api.Project
import java.nio.file.Path

abstract class FeatherCore(project: Project) {

    var discord: DiscordExtension = project.extensions.create("discord", DiscordExtension::class.java)

    var rootDirectory: Path? = null

}