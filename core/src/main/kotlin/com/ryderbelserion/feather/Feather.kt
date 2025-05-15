package com.ryderbelserion.feather

import com.ryderbelserion.feather.plugin.FeatherCore
import com.ryderbelserion.feather.discord.tasks.Webhook
import org.gradle.api.Plugin
import org.gradle.api.Project

class Feather : Plugin<Project> {

    override fun apply(project: Project) {
        val feather = project.extensions.create("feather", FeatherCore::class.java)

        project.afterEvaluate {
            feather.discord.webhooks.forEach { id ->
                project.tasks.register(id.value.task(), Webhook::class.java) {
                    val taskGroup = id.value.group()

                    if (taskGroup.isNotEmpty() || !taskGroup.equals("N/A", ignoreCase = true)) {
                        it.group = taskGroup
                    }

                    it.task = id.value.task()
                }
            }
        }
    }
}