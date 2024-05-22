package com.ryderbelserion.feather

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler

public class FeatherLogic : Plugin<Project> {

    override fun apply(target: Project) {}

}

public fun DependencyHandler.feather(version: String) {
    add("implementation", "com.ryderbelserion.feather:feather-core:$version")
}