/*plugins {
    `maven-publish`

    //kotlin("jvm") version "1.9.23"

    id("com.gradle.plugin-publish") version "1.2.1"

    //alias(libs.plugins.shadowjar)
}

base {
    archivesName.set(rootProject.name)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    api("com.lordcodes.turtle:turtle:0.9.0")

    val ktor = "2.3.9"

    implementation("io.ktor:ktor-client-core-jvm:$ktor")
    implementation("io.ktor:ktor-client-cio-jvm:$ktor")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor")
    implementation("io.ktor:ktor-serialization-gson:$ktor")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
}

val javaComponent: SoftwareComponent = components["java"]

kotlin {
    jvmToolchain(21)
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "21"
            javaParameters = true
        }
    }

    val sourcesJar by creating(Jar::class) {
        archiveClassifier.set("sources")
        from(sourceSets.main.get().allSource)
    }

    //shadowJar {
    //    archiveClassifier.set("")

    //    exclude("META-INF/**")
    //}

    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = rootProject.group.toString()
                artifactId = rootProject.name.lowercase()
                version = rootProject.version.toString()

                from(javaComponent)
            }
        }

        repositories {
            maven {
                credentials {
                    this.username = System.getenv("gradle_username")
                    this.password = System.getenv("gradle_password")
                }

                url = uri("https://repo.crazycrew.us/releases")
            }
        }
    }
}

gradlePlugin {
    plugins.create("feather") {
        id = "com.ryderbelserion.feather"
        displayName = "feather"
        tags.set(listOf("paper", "minecraft"))
        website.set("https://github.com/ryderbelserion/Feather")
        vcsUrl.set("https://github.com/ryderbelserion/Feather")
        description = "A neat little gradle plugin with anything I need."
        implementationClass = "com.ryderbelserion.feather.FeatherPlugin"
        version = rootProject.version
    }
}
 */