plugins {
    id("com.gradle.plugin-publish")

    id("kotlin-plugin")
}

val prefix = project.name.substringAfter("feather-")

gradlePlugin {
    website.set("https://github.com/ryderbelserion/Feather")
    vcsUrl.set("https://github.com/ryderbelserion/Feather")

    plugins.create("feather-$prefix") {
        id = "com.ryderbelserion.feather-$prefix"
        displayName = "feather $prefix"
    }
}

publishing {
    repositories {
        maven("https://repo.crazycrew.us/releases") {
            credentials {
                this.username = providers.gradleProperty("crazycrew.repo.username").get()
                this.password = providers.gradleProperty("crazycrew.repo.password").get()
            }
        }
    }

    publications {
        withType(MavenPublication::class).configureEach {
            pom {
                val repoPath = "ryderbelserion/feather"
                val repoUrl = "https://github.com/$repoPath"

                name.set("feather")
                description.set("a gradle plugin with a slew of neat utilities")
                url.set(repoUrl)
                inceptionYear.set("2024")

                licenses {
                    license {
                        name.set("MIT")
                        url.set("$repoUrl/blob/main/license/LICENSE")
                        distribution.set("repo")
                    }
                }

                issueManagement {
                    system.set("GitHub")
                    url.set("$repoUrl/issues")
                }

                developers {
                    developer {
                        id.set("ryderbelserion")
                        name.set("Ryder Belserion")
                        email.set("no-reply@ryderbelserion.com")
                        url.set("https://github.com/ryderbelserion")
                    }
                }

                scm {
                    url.set(repoUrl)
                    connection.set("scm:git:$repoUrl.git")
                    developerConnection.set("scm:git:git@github.com:$repoPath.git")
                }
            }
        }
    }
}