plugins {
    kotlin("jvm")

    idea
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(gradleKotlinDsl())
    compileOnly(gradleApi())
}

kotlin {
    jvmToolchain(21)

    explicitApi()
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