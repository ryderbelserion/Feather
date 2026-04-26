plugins {
    id("com.ryderbelserion.feather.core")
}

repositories {
    mavenCentral()
}

feather {
    workingDirectory = rootProject.rootDir.toPath()

    val builder = feather.getBuilder()

    val origin = builder.getNewestCommit(
        "ryderbelserion",
        "Feather",
        "0cc39afc46e3ca836e32a6f2a083146a3335d5c7"
    )

    val commit = origin?.commit
    val author = commit?.author

    val user = origin?.user

    val msg = commit?.message
    //val sha = commit?.tree?.sha

    val stats = origin?.stats

    discord {
        webhook {
            group("feather")
            task("webhook")

            post("")

            username(author?.name ?: "N/A")

            avatar(user?.getAvatar() ?: "N/A")

            embeds {
                embed {
                    title("Changelog")

                    description(msg ?: "N/A")

                    color("#e91e63")

                    val total = stats?.total
                    val deletions = stats?.deletions
                    val additions = stats?.additions

                    fields {
                        field("Total Lines", total.toString(), true)
                        field("Deletions", deletions.toString(), true)
                        field("Additions", additions.toString(), true)
                        field("Link", origin?.url ?: "N/A")
                    }
                }
            }
        }
    }
}

tasks.register("branch") {
    description = "prints branch information"
    group = "feather"

    val git = feather.getBuilder().utils

    println("Branch ${git.getRemoteBranch()}")
    println("Commit ${git.getRemoteCommitHash()}")
}

tasks.register("print") {
    description = "prints debug information"
    group = "feather"

    val builder = feather.getBuilder()

    val origin = builder.getNewestCommit(
        "ryderbelserion",
        "Feather",
        "0cc39afc46e3ca836e32a6f2a083146a3335d5c7"
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