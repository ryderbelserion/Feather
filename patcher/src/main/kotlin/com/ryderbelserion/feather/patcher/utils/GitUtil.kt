package com.ryderbelserion.feather.patcher.utils

import com.ryderbelserion.feather.patcher.api.FeatherException
import java.io.BufferedReader
import java.nio.file.Path
import java.util.concurrent.TimeUnit

class GitUtil {

    fun getRemoteCommitMessage(target: Path, hash: String, format: String): String = git(target, "show", "-s", "--format=$format", hash)

    fun getRemoteCommitMessage(target: Path, format: String): String = getRemoteCommitMessage(target, getRemoteCommitHash(target), format)

    fun getRemoteCommitHash(target: Path) : String = git(target, "rev-parse", getRemoteBranch(target))

    fun getRemoteBranch(target: Path): String = git(target, "branch", "--show-current")

    fun checkoutUpstreamRepository(target: Path, upstreamBranch: String, upstreamUrl: String) {
        git(target, "init", "--quiet")
        git(target, "remote", "remove", upstreamBranch)
        git(target, "remote", "add", upstreamBranch, upstreamUrl)

        git(target, "fetch", upstreamBranch, "--prune", "--prune-tags", "--force")

        git(target, "reset", "--hard", upstreamBranch)
        git(target, "gc")
    }

    private fun git(target: Path, vararg arguments: String): String = command(target, *arguments)

    private fun command(target: Path, vararg arguments: String): String {
        val process = ProcessBuilder("git", *arguments).directory(target.toFile()).start()

        process.waitFor(10, TimeUnit.SECONDS)

        return process.retrieveOutput()
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