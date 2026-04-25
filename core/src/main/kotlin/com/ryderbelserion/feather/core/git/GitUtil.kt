package com.ryderbelserion.feather.core.git

import com.ryderbelserion.feather.core.api.FeatherException
import java.io.BufferedReader
import java.nio.file.Path
import java.util.concurrent.TimeUnit

class GitUtil(private val workingDirectory: Path) {

    fun getRemoteCommitMessage(hash: String, format: String): String = git(listOf("show", "-s", "--format=$format", hash))

    fun getRemoteCommitMessage(format: String): String = getRemoteCommitMessage(getRemoteCommitHash(), format)

    fun getRemoteCommitHash(): String = git(listOf("rev-parse", getRemoteBranch()))

    fun getRemoteBranch(): String = git(listOf("rev-parse", "origin/HEAD"))

    private fun git(arguments: List<String>): String = command(arguments)

    private fun command(arguments: List<String> = listOf()): String {
        val splitter = listOf("git") + arguments

        val process = ProcessBuilder().directory(this.workingDirectory.toFile()).command(splitter).start()

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