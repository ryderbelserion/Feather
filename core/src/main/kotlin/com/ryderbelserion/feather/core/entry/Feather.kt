package com.ryderbelserion.feather.core.entry

import com.ryderbelserion.feather.core.FeatherCore
import com.ryderbelserion.feather.core.modules.webhooks.tasks.Webhook
import org.gradle.api.Plugin
import org.gradle.api.Project

class Feather : Plugin<Project> {

    override fun apply(target: Project) {
        val feather = target.extensions.create("feather", FeatherCore::class.java)

        target.afterEvaluate {
            target.afterEvaluate(feather)
        }
    }

    private fun Project.afterEvaluate(feather: FeatherCore) {
        feather.discord.webhooks.forEach { id ->
            tasks.register(id.value.task(), Webhook::class.java) {
                val taskGroup = id.value.group()

                if (taskGroup.isNotEmpty() || !taskGroup.equals("N/A", ignoreCase = true)) {
                    it.group = taskGroup
                }

                it.task = id.value.task()
            }
        }
    }
}