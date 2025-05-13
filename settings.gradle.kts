pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }

    includeBuild("./plugin")
}

rootProject.name = "Feather"

include("test")