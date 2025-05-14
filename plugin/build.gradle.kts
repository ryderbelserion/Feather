import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("com.gradle.plugin-publish") version "1.3.1"

    kotlin("plugin.serialization") version "2.1.20"
    kotlin("jvm") version "2.1.20"
}

project.group = "com.ryderbelserion.feather"
project.version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    val ktor = "3.1.3"

    implementation("io.ktor","ktor-client-content-negotiation", ktor)
    implementation("io.ktor","ktor-serialization-kotlinx-json", ktor)
    implementation("io.ktor","ktor-client-core-jvm", ktor)
    implementation("io.ktor","ktor-client-cio-jvm", ktor)

    implementation("org.jetbrains.kotlinx", "kotlinx-serialization-json", "1.8.1")

    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "1.10.2")
}

gradlePlugin {
    website = "https://github.com/ryderbelserion/Feather"
    vcsUrl = "https://github.com/ryderbelserion/Feather.git"

    plugins {
        create("feather") {
            displayName = "Feather"
            description = "Provides a set of opinionated utilities that may or may not make your life easier."
            group = "com.ryderbelserion.feather"
            id = "com.ryderbelserion.feather"
            tags = listOf("kotlin", "utility")

            implementationClass = "com.ryderbelserion.feather.Feather"
        }
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
        javaParameters = true
    }

    jvmToolchain(21)
}