import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version "2.1.20"

    id("com.ryderbelserion.feather.core")
}

project.version = "0.2.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.lordcodes.turtle", "turtle", "0.10.0")
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
        javaParameters = true
    }

    jvmToolchain(21)
}

feather {
    rootDirectory = rootProject.rootDir.toPath()

    discord {
        webhook {
            post("")

            task("notify_snapshot")
            group("crazycrates")

            username("Ryder Belserion")

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

            username("Ryder Belserion")

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