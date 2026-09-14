plugins {
    id("com.gradle.plugin-publish")
    id("com.gradleup.shadow")
}

gradlePlugin {
    website = "https://github.com/ryderbelserion/Feather"
    vcsUrl = "https://github.com/ryderbelserion/Feather.git"
}

tasks {
    shadowJar {
        archiveClassifier.set("")

        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }
}