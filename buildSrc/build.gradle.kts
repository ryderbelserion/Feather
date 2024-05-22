plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()

    mavenCentral()
}

dependencies {
    implementation(libs.gradle.kotlin.dsl)
    implementation(libs.gradle.publish)
    implementation(libs.gradle.shadow)
}