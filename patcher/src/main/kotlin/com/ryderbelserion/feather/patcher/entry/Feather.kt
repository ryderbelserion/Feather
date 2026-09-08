package com.ryderbelserion.feather.patcher.entry

import com.ryderbelserion.feather.patcher.FeatherPatcher
import com.ryderbelserion.feather.patcher.entry.tasks.types.ApplyTask
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

        val workingDirectory = feather.workingDirectory
        val patchesDirectory = feather.patchesDirectory
        val targetDirectory = feather.targetDirectory

        val sha = feather.sha
        val url = feather.url

        tasks.register("init", InitTask::class.java) { task ->
            task.group = group

            task.workingDirectory.set(workingDirectory)
            task.patchesDirectory.set(patchesDirectory)
            task.targetDirectory.set(targetDirectory)

            task.url.set(url)
            task.sha.set(sha)

            task.init()
        }

        tasks.register("apply", ApplyTask::class.java) { task ->
            task.group = group

            task.workingDirectory.set(workingDirectory)
            task.patchesDirectory.set(patchesDirectory)
            task.targetDirectory.set(targetDirectory)

            task.url.set(url)
            task.sha.set(sha)

            task.init()
        }
    }
}