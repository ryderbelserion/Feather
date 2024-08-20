package com.ryderbelserion.feather.utils

import com.lordcodes.turtle.shellRun
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import kotlin.system.exitProcess

public object GitUtil {

    private fun exit(process: Process) {
        val start = System.currentTimeMillis()

        while (process.isAlive) {
            if (start + 1000 * 60 < System.currentTimeMillis()) throw RuntimeException("The git process took too long! " + process.isAlive)

            runCatching {
                Thread.sleep(100)
            }.onFailure {
                it.printStackTrace()
            }
        }
    }

    public fun gitCommand(directory: File?, command: Array<String?>): Boolean {
        return try {
            val builder = ProcessBuilder(*command)
            builder.redirectErrorStream(true)
            builder.directory(directory)

            val starter = builder.start()
            val reader = BufferedReader(InputStreamReader(starter.inputStream))
            var line: String?

            while (reader.readLine().also { line = it } != null) {
                println(line)
            }

            exit(starter)

            starter.exitValue() != 0
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    public fun checkForGit() {
        runCatching {
            shellRun("git", listOf("--version"))
        }.onFailure {
            println("[Feather] Git was not found! Exiting now...")
            exitProcess(1)
        }
    }
}