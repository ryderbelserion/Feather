plugins {
    id("com.ryderbelserion.feather.core")
}

repositories {
    mavenCentral()
}

feather {
    workingDirectory = rootProject.rootDir.toPath()
}

tasks.register("print") {
    description = "prints debug information"
    group = "feather"

    val builder = feather.getBuilder()

    val origin = builder.getNewestCommit(
        "ryderbelserion",
        "Feather",
        "5e60d5be62ec603ffb72258f2e4cd74c2b1d2f65"
    )

    val commit = origin?.commit
    val author = commit?.author

    val user = origin?.user

    println("User: ${user?.name}, Id: ${user?.id}")

    println("Author: ${author?.name}, Email: ${author?.email}, Date: ${author?.date}")

    val msg = commit?.message
    val sha = commit?.tree?.sha

    println("Sha: $sha, Msg: $msg")

    val stats = origin?.stats

    println("Total: ${stats?.total}, Additions: ${stats?.additions}, Deletions: ${stats?.deletions}")
}