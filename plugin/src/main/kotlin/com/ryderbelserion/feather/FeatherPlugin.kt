package com.ryderbelserion.feather

import com.ryderbelserion.feather.patches.tasks.RebuildTask
import com.ryderbelserion.feather.patches.tasks.PatchTask
import com.ryderbelserion.feather.patches.PatcherExtension
import com.ryderbelserion.feather.patches.tasks.CommitTask
import com.ryderbelserion.feather.utils.GitUtils
import com.ryderbelserion.feather.webhook.WebhookTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class FeatherPlugin : Plugin<Project> {

    override fun apply(project: Project): Unit = with(project) {
        GitUtils().checkForGit()

        project.tasks.register("applyPatches", PatchTask::class.java) {
            group = "feather"

            it.dependsOn(extensions.create("patcher", PatcherExtension::class.java))
        }

        project.tasks.register("rebuildPatches", RebuildTask::class.java) {
            group = "feather"

            it.dependsOn(extensions.create("webhook", RebuildTask::class.java))
        }

        project.tasks.register("commitPatches", CommitTask::class.java) {
            group = "feather"

            it.dependsOn(extensions.create("webhook", CommitTask::class.java))
        }

        project.tasks.register("webhook", WebhookTask::class.java) {
            group = "feather"

            it.dependsOn(extensions.create("webhook", WebhookTask::class.java))
        }
    }
}