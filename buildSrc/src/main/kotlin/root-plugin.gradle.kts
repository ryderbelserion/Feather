plugins {
    id("com.github.johnrengelman.shadow")
    id("org.gradle.kotlin.kotlin-dsl")
    id("com.gradle.plugin-publish")
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    api(kotlin("stdlib"))

    implementation("com.lordcodes.turtle:turtle:0.8.0")

    val ktor = "2.2.4"

    implementation("io.ktor:ktor-client-core-jvm:$ktor")
    implementation("io.ktor:ktor-client-cio-jvm:$ktor")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor")
    implementation("io.ktor:ktor-serialization-gson:$ktor")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}