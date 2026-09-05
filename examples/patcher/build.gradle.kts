plugins {
    id("com.ryderbelserion.feather.patcher")
}

repositories {
    mavenCentral()
}

patcher {
    workingDirectory.set(projectDir)
    patchesDirectory.set(projectDir.resolve("patches"))
    targetDirectory.set(projectDir.resolve("upstream"))

    url.set("git@github.com:ryderbelserion/Test.git")
    sha.set("0878be42c6ac92d3313acdd770f5423185f5e03b")

    group = "feather"
}