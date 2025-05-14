pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }

    includeBuild("./core")
}

rootProject.name = "Feather"

include("test")