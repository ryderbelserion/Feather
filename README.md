# Feather
Provides a set of opinionated utilities that may or may not make your life easier.

### Features
- Ability to customize and send multiple embeds to different webhooks.

### Usage
```kotlin
plugins {
    id("com.ryderbelserion.feather.core") version "0.2.0"
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
```
