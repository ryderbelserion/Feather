import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("plugin.serialization") version "2.1.20"
    kotlin("jvm") version "2.1.20"

    id("com.ryderbelserion.feather.core")
}

project.version = "0.3.0"

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

    implementation("com.lordcodes.turtle", "turtle", "0.10.0")
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
        javaParameters = true
    }

    jvmToolchain(21)
}

tasks.register("debug") {
    val git = feather.getGit()
    val data = git.getCurrentCommitAuthorData()

    println("Author: ${data.author}, Avatar: ${data.avatar}, Email: ${data.email}")
}

feather {
    rootDirectory = rootProject.rootDir.toPath()

    val git = getGit()
    val data = git.getCurrentCommitAuthorData()

    discord {
        webhook {
            if (System.getenv("BUILD_WEBHOOK") != null) {
                post(System.getenv("BUILD_WEBHOOK"))
            }

            task("notify_snapshot")
            group("crazycrates")

            username(data.author)

            avatar(data.avatar)

            content("This is a snapshot of ${rootProject.name}")

            embeds {
                embed {
                    title("Version Information")

                    description("${project.version}-SNAPSHOT")

                    color("#e91e63")
                }
            }
        }

        webhook {
            if (System.getenv("BUILD_WEBHOOK") != null) {
                post(System.getenv("BUILD_WEBHOOK"))
            }

            task("notify_release")
            group("crazycrates")

            username(data.author)

            avatar(data.avatar)

            content("This is a release of ${rootProject.name}")

            embeds {
                embed {
                    title("Version Information")

                    description("${project.version}")

                    color("#ff9300")
                }
            }
        }
    }
}