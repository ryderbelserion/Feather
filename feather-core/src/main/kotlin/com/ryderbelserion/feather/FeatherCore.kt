package com.ryderbelserion.feather

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create

public class FeatherCore : Plugin<Project> {

    override fun apply(project: Project): Unit = with(project) {
        extensions.create<FeatherExtension>("feather", this)
    }
}