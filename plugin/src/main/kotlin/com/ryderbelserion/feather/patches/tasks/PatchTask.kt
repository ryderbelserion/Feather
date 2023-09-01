package com.ryderbelserion.feather.patches.tasks

import com.ryderbelserion.feather.utils.FileUtil
import com.ryderbelserion.feather.patches.PatcherExtension
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class PatchTask : DefaultTask() {

    @get:Input
    lateinit var extension: PatcherExtension

    @TaskAction
    fun feather() {
        val feather = extension.build()

        // Create the workspace directory.
        if (feather.plugin.workspace.isEmpty()) {
            println("[Feather] The workspace name cannot be empty.")
            return
        }

        val directory: File = if (feather.plugin.path != null) {
            feather.plugin.path.toFile()
        } else {
            project.rootDir
        }

        directory.mkdirs()

        val workspace = File("$directory/${feather.plugin.workspace}")
        if (!workspace.exists()) {
            println("[Feather] Creating the workspace folder.")
            workspace.mkdirs()
        }

        val patchFolder = File("$directory/patches")
        if (!patchFolder.exists()) {
            println("[Feather] Creating the patches folder.")
            patchFolder.mkdirs()
        }

        FileUtil().getUpstream(directory, workspace, feather.upstream.autoUpstream, feather.upstream.url, feather.upstream.sha, feather.upstream.branch)
        FileUtil().applyPatches(workspace, patchFolder)
    }
}