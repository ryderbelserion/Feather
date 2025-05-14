package com.ryderbelserion.feather

import com.ryderbelserion.feather.webhook.Webhook
import com.ryderbelserion.feather.webhook.builders.MessageBuilder
import org.gradle.api.Plugin
import org.gradle.api.Project

class Feather : Plugin<Project> {

    override fun apply(target: Project) {
        val extension = target.extensions.create("webhook", MessageBuilder::class.java)

        target.tasks.register("webhook", Webhook::class.java) {
            it.extension = extension
            it.group = "feather"
        }
    }
}