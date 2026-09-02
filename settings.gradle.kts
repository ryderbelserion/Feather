rootProject.name = "Feather"

includeBuild("build-logic")
includeBuild("patcher")
//includeBuild("core")

listOf(
    "examples/patcher" to "patcher",
    //"examples/core" to "core"
).forEach {
    includeProject(it)
}

fun includeProject(pair: Pair<String, String>): Unit = includeProject(pair.first, pair.second)

fun includeProject(name: String, block: ProjectDescriptor.() -> Unit) {
    include(name)
    project(":$name").apply(block)
}

fun includeProject(path: String, name: String) {
    includeProject(name) {
        this.name = "${rootProject.name.lowercase()}-$name"
        this.projectDir = File(path)
    }
}

fun includeProject(name: String) {
    includeProject(name) {
        this.name = "${rootProject.name.lowercase()}-$name"
    }
}