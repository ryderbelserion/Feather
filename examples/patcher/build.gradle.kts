plugins {
    id("com.ryderbelserion.feather.patcher")
}

repositories {
    mavenCentral()
}

val path = projectDir.resolve("src")

patcher {
    patchesDirectory.set(projectDir.resolve("patches"))
    targetDirectory.set(path.resolve("target"))
    workingDirectory.set(path)

    url.set("git@github.com:ryderbelserion/Test.git")
    sha.set("0878be42c6ac92d3313acdd770f5423185f5e03b")

    group = "feather"
}