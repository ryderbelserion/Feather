plugins {
    kotlin("jvm")

    idea
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(gradleApi())
}

kotlin {
    jvmToolchain(21)
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