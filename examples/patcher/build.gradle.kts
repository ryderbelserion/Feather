plugins {
    id("com.ryderbelserion.feather.patcher")
}

repositories {
    mavenCentral()
}

patcher {
    targetProject = "git@github.com:ryderbelserion/Test.git"

    commitHash = "0878be42c6ac92d3313acdd770f5423185f5e03b"

    workingDirectory = project.projectDir.toPath()
    patchesDirectory = workingDirectory.resolve("patches")
    targetDirectory = workingDirectory.resolve("target")

    group = "feather"
}