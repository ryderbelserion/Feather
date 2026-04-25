/*feather {
    val github = feather.getGit().getGithubCommit("ryderbelserion/Feather")

    val user = github.user

    discord {
        webhook {
            group(rootProject.name.lowercase())
            content("This is content!")
            task("debug")

            post("")

            username("Ryder Belserion")

            avatar(user.avatar)

            embeds {
                embed {
                    title("This is a title")

                    description("This is a description")

                    color("#e91e63")
                }
            }
        }
    }
}*/