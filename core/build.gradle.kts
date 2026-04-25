import tools.configurePlugin

plugins {
    id("publish-config")
    id("kotlin-plugin")
}

project.group = "com.ryderbelserion.feather.core"
project.version = "0.5.0"

dependencies {
    implementation(libs.kotlin.coroutines)
    implementation(libs.bundles.ktor)
    implementation(libs.kotlin.json)
}

gradlePlugin {
    configurePlugin("core") {
        implementationClass = "${project.group}.entry.Feather"
        description = "Provides a set of opinionated utilities that may or may not make your life easier."
        tags.set(listOf("kotlin", "utility"))
    }
}