package com.ryderbelserion.feather.patcher.entry

import com.ryderbelserion.feather.patcher.FeatherPatcher
import com.ryderbelserion.feather.patcher.entry.tasks.types.InitTask
import org.gradle.api.Plugin
import org.gradle.api.Project

abstract class Feather : Plugin<Project> {

    override fun apply(target: Project) {
        val feather = target.extensions.create("patcher", FeatherPatcher::class.java)

        target.afterEvaluate {
            target.afterEvaluate(feather)
        }
    }

    private fun Project.afterEvaluate(feather: FeatherPatcher) {
        val group = feather.group

        tasks.register("init", InitTask::class.java) { task ->
            task.group = group

            task.workingDirectory.set(feather.workingDirectory)
            task.patchesDirectory.set(feather.patchesDirectory)
            task.targetDirectory.set(feather.targetDirectory)

            task.init()
        }
    }
}