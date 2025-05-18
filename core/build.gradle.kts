import other.configurePlugin

plugins {
    id("com.gradle.plugin-publish") version "1.3.1"

    kotlin("plugin.serialization") version "2.1.20"

    id("kotlin-plugin")
}

project.group = "com.ryderbelserion.feather.core"
project.version = "0.3.1"

dependencies {
    implementation(libs.bundles.ktor)

    implementation(libs.coroutines)
    implementation(libs.json)
}

gradlePlugin {
    website = "https://github.com/ryderbelserion/Feather"
    vcsUrl = "https://github.com/ryderbelserion/Feather.git"

    configurePlugin("core") {
        implementationClass = "${project.group}.Feather"
        description = "Provides a set of opinionated utilities that may or may not make your life easier."
    }
}