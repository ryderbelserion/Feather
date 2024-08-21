package com.ryderbelserion.feather

import com.ryderbelserion.feather.patches.PatcherExtension
import com.ryderbelserion.feather.patches.tasks.CommitTask
import com.ryderbelserion.feather.patches.tasks.PatchTask
import com.ryderbelserion.feather.patches.tasks.RebuildTask
import com.ryderbelserion.feather.utils.GitUtil
import org.gradle.api.Plugin
import org.gradle.api.Project

public class FeatherPatcher : Plugin<Project> {

    override fun apply(target: Project) {
        GitUtil.checkForGit()

        target.tasks.register("applyPatches", PatchTask::class.java) {
            it.group = "feather"

            it.dependsOn(it.extensions.create("patcher", PatcherExtension::class.java))
        }

        target.tasks.register("rebuildPatches", RebuildTask::class.java) {
            it.group = "feather"

            it.dependsOn(it.extensions.create("webhook", RebuildTask::class.java))
        }

        target.tasks.register("commitPatches", CommitTask::class.java) {
            it.group = "feather"

            it.dependsOn(it.extensions.create("webhook", CommitTask::class.java))
        }
    }
}