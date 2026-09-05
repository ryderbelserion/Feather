package com.ryderbelserion.feather.patcher.entry.tasks.types

import com.ryderbelserion.feather.patcher.api.Git
import com.ryderbelserion.feather.patcher.entry.tasks.BaseTask
import com.ryderbelserion.feather.patcher.utils.createDirectory
import com.ryderbelserion.feather.patcher.utils.toPath
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction

abstract class InitTask : BaseTask() {

    @get:InputDirectory
    abstract val workingDirectory: DirectoryProperty
    @get:InputDirectory
    abstract val patchesDirectory: DirectoryProperty
    @get:InputDirectory
    abstract val targetDirectory: DirectoryProperty

    @get:Input
    abstract val url: Property<String>

    @get:Input
    abstract val sha: Property<String>

    override fun init() {
        this.workingDirectory.createDirectory()
        this.patchesDirectory.createDirectory()
        this.targetDirectory.createDirectory()

        val path = this.targetDirectory.toPath()

        val git = Git(path, this.url.get(), this.sha.get())

        git.createUpstream("upstream")
    }

    @TaskAction
    fun run() {

    }
}