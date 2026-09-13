package com.ryderbelserion.feather.core.api.git

import com.ryderbelserion.feather.core.api.exceptions.FeatherException
import java.io.BufferedReader
import java.nio.file.Path
import java.util.concurrent.TimeUnit

class Git(private val parent: Path) {

    fun getRemoteCommitMessage(hash: String, format: String): String = git("show", "-s", "--format=$format", hash)

    fun getRemoteCommitMessage(format: String): String = getRemoteCommitMessage(getRemoteCommitHash(), format)

    fun getRemoteCommitHash() : String = git("rev-parse", getRemoteBranch())

    fun getRemoteBranch(): String = git("branch", "--show-current")

    fun getLocalBranch(): String = git("branch", "-r")

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
                println("There was an error using git ${arguments.contentToString()}")
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