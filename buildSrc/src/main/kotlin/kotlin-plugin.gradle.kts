import gradle.kotlin.dsl.accessors._d71484981518fd8dfd51d51ba90baf12.publishing

plugins {
    kotlin("jvm")

    idea
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(gradleKotlinDsl())
    compileOnly(gradleApi())
}

kotlin {
    jvmToolchain(21)

    explicitApi()
}

tasks {
    compileKotlin {
        charset("utf-8")

        kotlinOptions {
            jvmTarget = "21"
            javaParameters = true
        }
    }
}