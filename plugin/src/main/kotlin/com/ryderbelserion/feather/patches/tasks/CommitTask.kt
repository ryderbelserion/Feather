package com.ryderbelserion.feather.patches.tasks

import com.ryderbelserion.feather.utils.FileUtil
import com.ryderbelserion.feather.patches.PatcherExtension
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class CommitTask : DefaultTask() {

    @get:Input
    lateinit var extension: PatcherExtension

    @TaskAction
    fun feather() {
        val feather = extension.build()

        val directory: File = if (feather.plugin.path != null) {
            feather.plugin.path.toFile()
        } else {
            project.rootDir
        }

        val workspace = File("$directory/${feather.plugin.workspace}")

        FileUtil().commit(workspace)
    }
}