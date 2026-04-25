import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("org.jetbrains.kotlin.plugin.serialization")

    kotlin("jvm")
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_25)
        javaParameters = true
    }

    jvmToolchain(25)
}