package com.ryderbelserion.feather.patcher.entry

import com.ryderbelserion.feather.patcher.FeatherPatcher
import com.ryderbelserion.feather.patcher.utils.GitUtil
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

                val patches = feather.patchesDirectory

                if (!Files.exists(patches)) {
                    Files.createDirectory(patches)
                }

                val target = feather.targetDirectory

                if (!Files.exists(target)) {
                    Files.createDirectory(target)
                }

                GitUtil().checkoutUpstreamRepository(target, feather.targetBranch, feather.targetProject)

                val taskGroup = feather.group

                if (taskGroup.isNotEmpty() || !taskGroup.equals("N/A", ignoreCase = true)) {
                    it.group = taskGroup
                }
            }

            target.tasks.register("debug") {
                val directory = feather.workingDirectory

                val patches = feather.patchesDirectory

                val target = feather.targetDirectory

                val taskGroup = feather.group

                if (taskGroup.isNotEmpty() || !taskGroup.equals("N/A", ignoreCase = true)) {
                    it.group = taskGroup
                }

                println("Directory $directory, Patches: $patches, Target: $target")
            }
        }
    }
}