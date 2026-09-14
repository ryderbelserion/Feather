package com.ryderbelserion.feather.patcher.api

import com.ryderbelserion.feather.patcher.api.exceptions.FeatherException
import com.ryderbelserion.feather.patcher.utils.asPath
import com.ryderbelserion.feather.patcher.utils.error
import com.ryderbelserion.feather.patcher.utils.info
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

class Git(private val parent: Path, private val url: String, private val sha: String) {

    fun getRemoteCommitMessage(hash: String, format: String): String = git("show", "-s", "--format=$format", hash)

    fun getRemoteCommitMessage(format: String): String = getRemoteCommitMessage(getRemoteCommitHash(), format)

    fun getRemoteCommitHash() : String = git("rev-parse", getRemoteBranch())

    fun getRemoteBranch(): String = git("branch", "--show-current")

    fun getLocalBranch(): String = git("branch", "-r")

    fun disableGpgSigning() {
        git(false, "commit.gpgSign", "false")
        git(false, "tag.gpgSign", "false")
    }

    fun createUpstream(target: Path, origin: String) {
        git(target, false, "clone", this.url, origin)

        git(origin.asPath(target), "branch", "-f", origin, this.sha)

        git(origin.asPath(target), "checkout", origin)
    }

    @OptIn(ExperimentalPathApi::class)
    fun createWorkspace(source: Path, origin: String) {
        if (this.parent.resolve(".git").notExists()) {
            this.parent.deleteRecursively()
            this.parent.createDirectories()

            git("init", "--quiet")
        }

        git(false, "remote", "add", origin, source.absolutePathString())

        git("fetch", origin)

        git("reset", "--hard", origin)
    }

    fun applyPatches(path: Path, target: Path) {
        path.matching("*.patch").forEach {
            val name = it.fileName.toString()

            runCatching {
                "Applying patch $name to project!".info()

                git(target, "am", "--3way", "--ignore-whitespace", it.absolutePathString())
            }.onFailure {
                "Failed to apply patch $name to project! Please resolve the merge conflict, and try again.".error()

                exitProcess(1)
            }.onSuccess {
                "Applied patch $name to project!".info()
            }
        }
    }

    fun savePatches(path: Path, origin: String) {
        git(
            "format-patch",
            "--no-stat",
            "--zero-commit",
            "--full-index",
            "--no-signature",
            "--no-numbered",
            "--no-stat",
            "--quiet",
            "-N",
            "-o", path.absolutePathString(),
            "$origin/$origin"
        )
    }

    fun git(target: Path, verbose: Boolean, vararg arguments: String) = command(target, verbose, *arguments)

    fun git(target: Path, vararg arguments: String) = command(target, true, *arguments)

    fun git(verbose: Boolean, vararg arguments: String): String = git(this.parent, verbose, *arguments)

    fun git(vararg arguments: String) = git(true, *arguments)

    private fun command(target: Path, verbose: Boolean, vararg arguments: String): String {
        val process = ProcessBuilder("git", *arguments).directory(target.toFile())

        return runCatching {
            val index = process.start()

            index.waitFor(10, TimeUnit.SECONDS)

            return index.retrieveOutput()
        }.onFailure {
            if (verbose) {
                "There was an error while checking ${this.url} using git ${arguments.contentToString()}".error()
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