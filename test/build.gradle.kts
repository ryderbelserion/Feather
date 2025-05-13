import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("plugin.serialization") version "2.1.20"
    kotlin("jvm") version "2.1.20"

    id("feather")
}

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

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
        javaParameters = true
    }

    jvmToolchain(21)
}

tasks {
    webhook {
        post("insert_discord_webhook_url_here")

        content("This is content!")

        username("Ryder Belserion")

        embeds {
            embed {
                title("This is a title")

                description("This is a description")

                color("#e91e63")
            }
        }
    }
}