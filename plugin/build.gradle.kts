plugins {
    `maven-publish`

    kotlin("jvm") version "1.9.21"

    id("com.gradle.plugin-publish") version "1.2.1"

    alias(libs.plugins.shadowjar)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation("com.lordcodes.turtle:turtle:0.9.0")

    val ktor = "2.3.7"

    implementation("io.ktor:ktor-client-core-jvm:$ktor")
    implementation("io.ktor:ktor-client-cio-jvm:$ktor")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor")
    implementation("io.ktor:ktor-serialization-gson:$ktor")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
}

val javaComponent: SoftwareComponent = components["java"]

kotlin {
    jvmToolchain(17)
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "17"
            javaParameters = true
        }
    }

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