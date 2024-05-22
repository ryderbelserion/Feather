package com.ryderbelserion.feather.records

import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPublication

public class PublishConfigure(private val project: Project) {
    private var configure: MavenPublication.() -> Unit = {}

    public fun configure(configure: MavenPublication.() -> Unit) {
        this.configure = configure
    }
}