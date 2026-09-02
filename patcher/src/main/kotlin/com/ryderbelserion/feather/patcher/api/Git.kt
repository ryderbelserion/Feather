package com.ryderbelserion.feather.patcher.api

import com.ryderbelserion.feather.patcher.api.exceptions.FeatherException
import java.io.BufferedReader
import java.nio.file.Path
import java.util.concurrent.TimeUnit
import kotlin.io.path.absolutePathString

class Git(private val repo: Path) {

    fun getRemoteCommitMessage(hash: String, format: String): String = git("show", "-s", "--format=$format", hash)

    fun getRemoteCommitMessage(format: String): String = getRemoteCommitMessage(getRemoteCommitHash(), format)

    fun getRemoteCommitHash() : String = git("rev-parse", getRemoteBranch())

    fun getRemoteBranch(): String = git("branch", "--show-current")

    fun disableGpgSigning() {
        git("commit.gpgSign", "false")
        git("tag.gpgSign", "false")
    }

    fun createUpstream(upstream: Path, upstreamBranch: String, branchName: String) {
        git("init", "--quiet")

        disableGpgSigning()

        git("remote", "add", upstreamBranch, upstream.absolutePathString())

        git("fetch", upstreamBranch, "--prune", "--prune-tags", "--force")

        git("checkout", branchName)

        git("reset", "--hard", upstreamBranch)
        git("gc")
    }

    private fun git(vararg arguments: String): String = command(*arguments)

    private fun command(vararg arguments: String): String {
        val process = ProcessBuilder("git", *arguments).directory(this.repo.toFile())

        return runCatching {
            val index = process.start()

            index.waitFor(10, TimeUnit.SECONDS)

            return index.retrieveOutput()
        }.onFailure {
            println("There was an error while checking $this.repo using git ${arguments.contentToString()}")

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