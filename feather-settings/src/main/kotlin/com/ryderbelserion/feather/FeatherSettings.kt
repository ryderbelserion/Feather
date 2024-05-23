package com.ryderbelserion.feather

import org.gradle.api.Plugin
import org.gradle.api.initialization.ProjectDescriptor
import org.gradle.api.initialization.Settings

public class FeatherSettings : Plugin<Settings> {

    override fun apply(target: Settings) {}

}

public val libs: String get() = "../gradle/libs.versions.toml"

public fun Settings.includeProject(name: String) {
    includeProject(name) {
        this.name = "${rootProject.name.lowercase()}-$name"
    }
}

public fun Settings.includeProject(name: String, block: ProjectDescriptor.() -> Unit, ) {
    include(name)
    project(":$name").apply(block)
}