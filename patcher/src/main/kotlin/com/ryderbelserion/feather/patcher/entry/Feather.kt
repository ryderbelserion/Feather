package com.ryderbelserion.feather.patcher.entry

import com.ryderbelserion.feather.patcher.FeatherPatcher
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.nio.file.Files

class Feather : Plugin<Project> {

    override fun apply(target: Project) {
        val feather = target.extensions.create("patcher", FeatherPatcher::class.java)

        target.afterEvaluate {
            target.tasks.register("init") {
                val directory = feather.workingDirectory

                if (!Files.exists(directory)) {
                    Files.createDirectory(directory)
                }

                val taskGroup = feather.group

                if (taskGroup.isNotEmpty() || !taskGroup.equals("N/A", ignoreCase = true)) {
                    it.group = taskGroup
                }
            }
        }
    }
}