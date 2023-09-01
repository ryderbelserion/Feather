plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("org.gradle.kotlin.kotlin-dsl:org.gradle.kotlin.kotlin-dsl.gradle.plugin:4.0.6")

    implementation("com.gradle.publish:plugin-publish-plugin:1.1.0")

    implementation("com.github.johnrengelman:shadow:8.1.1")

    val ktor = "2.2.4"

    implementation("io.ktor:ktor-client-core-jvm:$ktor")
    implementation("io.ktor:ktor-client-cio-jvm:$ktor")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor")
    implementation("io.ktor:ktor-serialization-gson:$ktor")

    implementation("com.lordcodes.turtle:turtle:0.8.0")
}