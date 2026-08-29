import tools.configurePlugin

plugins {
    id("publish-config")
    id("kotlin-plugin")
}

project.group = "com.ryderbelserion.feather.patcher"
project.version = "0.1.0"

dependencies {
    implementation(libs.kotlin.coroutines)
    implementation(libs.bundles.ktor)
    implementation(libs.kotlin.json)
}

gradlePlugin {
    configurePlugin("patcher") {
        implementationClass = "${project.group}.entry.Feather"
        description = "Provides the ability to create patch based forks."
        tags.set(listOf("kotlin", "utility"))
    }
}