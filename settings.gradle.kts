pluginManagement {
    repositories {
        gradlePluginPortal()

        mavenCentral()
    }
}

rootProject.name = "Feather"

include("feather-patcher", "feather-settings", "feather-core")