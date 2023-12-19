plugins {
    id("root-plugin")

    `maven-publish`
}

val javaComponent: SoftwareComponent = components["java"]

tasks {
    val sourcesJar by creating(Jar::class) {
        archiveClassifier.set("sources")
        from(sourceSets.main.get().allSource)
    }

    shadowJar {
        archiveClassifier.set("")

        exclude("META-INF/**")
    }

    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = rootProject.group.toString()
                artifactId = "${rootProject.name.lowercase()}-api"
                version = rootProject.version.toString()

                from(javaComponent)
            }
        }

        repositories {
            maven {
                credentials {
                    this.username = System.getenv("GRADLE_USERNAME")
                    this.password = System.getenv("GRADLE_PASSWORD")
                }

                url = uri("https://repo.crazycrew.us/first-party/")
            }
        }
    }
}

gradlePlugin {
    plugins {
        create("feather") {
            id = "featherpatcher"
            displayName = "Feather"
            description = "A neat little gradle plugin with anything I need."
            implementationClass = "com.ryderbelserion.feather.FeatherPlugin"
            version = rootProject.version
        }
    }
}