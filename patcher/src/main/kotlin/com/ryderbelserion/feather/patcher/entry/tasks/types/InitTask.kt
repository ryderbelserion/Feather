package com.ryderbelserion.feather.patcher.entry.tasks.types

import com.ryderbelserion.feather.patcher.entry.tasks.BaseTask
import com.ryderbelserion.feather.patcher.utils.createDirectory
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction

abstract class InitTask : BaseTask() {

    @get:InputDirectory
    abstract val workingDirectory: DirectoryProperty
    @get:InputDirectory
    abstract val patchesDirectory: DirectoryProperty
    @get:InputDirectory
    abstract val targetDirectory: DirectoryProperty

    override fun init() {
        this.workingDirectory.createDirectory()
        this.patchesDirectory.createDirectory()
        this.targetDirectory.createDirectory()
    }

    @TaskAction
    fun run() {

    }
}