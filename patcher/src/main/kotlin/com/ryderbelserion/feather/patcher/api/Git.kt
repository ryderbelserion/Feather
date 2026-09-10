package com.ryderbelserion.feather.patcher.api

import com.ryderbelserion.feather.patcher.api.exceptions.FeatherException
import com.ryderbelserion.feather.patcher.utils.matching
import java.io.BufferedReader
import java.nio.file.Path
import java.util.concurrent.TimeUnit
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.absolutePathString
import kotlin.io.path.createDirectories
import kotlin.io.path.deleteRecursively
import kotlin.io.path.notExists
import kotlin.system.exitProcess

class Git(private val repo: Path, private val url: String, private val sha: String) {

    fun getRemoteCommitMessage(hash: String, format: String): String = git("show", "-s", "--format=$format", hash)

    fun getRemoteCommitMessage(format: String): String = getRemoteCommitMessage(getRemoteCommitHash(), format)

    fun getRemoteCommitHash() : String = git("rev-parse", getRemoteBranch())

    fun getRemoteBranch(): String = git("branch", "--show-current")

    fun disableGpgSigning() {
        git(false, "commit.gpgSign", "false")
        git(false, "tag.gpgSign", "false")
    }

    @OptIn(ExperimentalPathApi::class)
    fun createUpstream(branch: String, origin: String) {
        if (this.repo.resolve(".git").notExists()) {
            this.repo.deleteRecursively()
            this.repo.createDirectories()

            git("init", "--quiet")
        }

        git(false, "remote", "add", origin, this.url)

        git("fetch", origin)

        git("reset", "--hard", this.sha)

        runCatching {
            git(false, "checkout", "-b", branch)
        }.onFailure {
            git("checkout", branch)
        }
    }

    fun applyPatches(path: Path) {
        path.matching("*.patch").forEach {
            val name = it.fileName.toString()

            runCatching {
                println("Applying patch $name to project!")

                git("am", "--3way", "--ignore-whitespace", it.absolutePathString())
            }.onFailure {
                println("Failed to apply patch $name to project! Please resolve the merge conflict, and try again.")

                exitProcess(1)
            }.onSuccess {
                println("Applied patch $name to project!")
            }
        }
    }

    fun savePatches(path: Path) {
        git(
            "format-patch",
            "--zero-commit",
            "--full-index",
            "--no-signature",
            "--no-stat",
            "--no-numbered",
            "-1",
            "HEAD",
            "-N",
            "-o", path.absolutePathString())

        git("reset", "--mixed", "HEAD~1")

        //git("format-patch", "-1", "HEAD", "--quiet", "-o", path.absolutePathString())
    }

    private fun git(isLogging: Boolean, vararg arguments: String): String = command(isLogging, *arguments)

    private fun git(vararg arguments: String) = git(true, *arguments)

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