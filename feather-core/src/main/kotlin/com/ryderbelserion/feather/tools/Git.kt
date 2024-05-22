package com.ryderbelserion.feather.tools

import org.gradle.api.Project
import java.io.ByteArrayOutputStream

public fun formatLog(hash: String, message: String, project: String, organization: String): String {
    return "[$hash](https://github.com/$organization/$project/commit/$hash) $message"
}

public fun Project.latestCommitHash(): String {
    return runGitCommand(listOf("rev-parse", "--short", "HEAD"))
}

public fun Project.latestCommitMessage(): String {
    return runGitCommand(listOf("log", "-1", "--pretty=%B"))
}

public fun Project.branchName(): String {
    return runGitCommand(listOf("rev-parse", "--abbrev-ref", "HEAD"))
}

public fun Project.runGitCommand(value: List<String>): String {
    val output: String = ByteArrayOutputStream().use {
        exec { git ->
            git.executable("git")
            git.args(value)
            git.standardOutput = it
        }

        it.toString(Charsets.UTF_8).trim()
    }

    return output
}