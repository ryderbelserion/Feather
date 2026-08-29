plugins {
    id("com.ryderbelserion.feather.patcher")
}

repositories {
    mavenCentral()
}

patcher {
    workingDirectory = project.projectDir.toPath().resolve("patches")
}