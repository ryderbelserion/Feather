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
    println(feather.getGit().getCurrentCommit())
}

feather {
    rootDirectory = rootProject.rootDir.toPath()

    val git = getGit()
    val item = git.getGithubInformation()
    val author = item.author

    discord {
        webhook {
            post("")

            task("notify_snapshot")
            group("crazycrates")

            username(author)

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
            post("")

            task("notify_release")
            group("crazycrates")

            username(author)

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