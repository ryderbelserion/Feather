package com.ryderbelserion.feather.patcher.api

import com.ryderbelserion.feather.patcher.api.exceptions.FeatherException
import com.ryderbelserion.feather.patcher.utils.matching
import java.io.BufferedReader
import java.nio.file.Path
import java.util.concurrent.TimeUnit
import kotlin.io.path.absolutePathString
import kotlin.system.exitProcess

class Git(private val repo: Path, private val url: String, private val sha: String) {

    fun getRemoteCommitMessage(hash: String, format: String): String = git(false, "show", "-s", "--format=$format", hash)

    fun getRemoteCommitMessage(format: String): String = getRemoteCommitMessage(getRemoteCommitHash(), format)

    fun getRemoteCommitHash() : String = git(false, "rev-parse", getRemoteBranch())

    fun getRemoteBranch(): String = git(false, "branch", "--show-current")

    fun disableGpgSigning() {
        git(false, "commit.gpgSign", "false")
        git(false, "tag.gpgSign", "false")
    }

    fun createUpstream(branch: String) {
        git(true, "init", "--quiet", "--initial-branch", branch)

        git(false, "remote", "add", "origin", this.url)

        git(true, "fetch", "origin")

        git(true, "reset", "--hard", this.sha)
    }

    fun applyPatches(path: Path) {
        path.matching("*.patch").forEach {
            val name = it.fileName.toString()

            runCatching {
                println("Applying patch $name to project!")

                git(true, "am", "--3way", "--ignore-whitespace", "--reject", it.absolutePathString())
            }.onFailure {
                println("Failed to apply patch $name to project! Please resolve the merge conflict, and try again.")

                exitProcess(1)
            }.onSuccess {
                println("Applied patch $name to project!")
            }
        }
    }

    private fun git(isLogging: Boolean = false, vararg arguments: String): String = command(isLogging, *arguments)

    private fun command(isLogging: Boolean, vararg arguments: String): String {
        val process = ProcessBuilder("git", *arguments).directory(this.repo.toFile())

        return runCatching {
            val index = process.start()

            index.waitFor(10, TimeUnit.SECONDS)

            return index.retrieveOutput()
        }.onFailure {
            if (isLogging) {
                println("There was an error while checking ${this.url} using git ${arguments.contentToString()}")
            }

            return ""
        }.toString()
    }

    private fun Process.retrieveOutput(): String {
        val output = inputStream.bufferedReader().use(BufferedReader::readText)

        val exitCode = exitValue()

        if (exitCode != 0) {
            val text = errorStream.bufferedReader().use(BufferedReader::readText)

            throw FeatherException("Failed to execute git command with code: %s", text)
        }

        return output.trim()
    }
}