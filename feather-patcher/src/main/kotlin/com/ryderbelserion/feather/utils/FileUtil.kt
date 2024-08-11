package com.ryderbelserion.feather.utils

import com.lordcodes.turtle.shellRun
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.util.*
import java.util.function.Predicate
import java.util.stream.Collectors
import kotlin.system.exitProcess


public object FileUtil {

    public fun getUpstream(directory: File, workspace: File, toggle: Boolean, url: String, sha: String, branch: String) {
        val upstream = File("$directory/upstream")

        upstream.delete()
        upstream.mkdirs()

        shellRun(upstream) {
            println("[Feather] Cloning into '${upstream.name}'...")
            git.clone(url, upstream.absolutePath)

            if (branch.isNotEmpty()) {
                println("[Feather] Checking out $branch directly after clone.")
                git.gitCommand(listOf("switch", branch))
            }

            val id: String = if (toggle) {
                git.gitCommand(listOf("show", "-s", "--format=%H"))
            } else {
                sha
            }

            println("[Feather] Preparing $url with commit: $id")

            git.gitCommand(listOf("branch", "-f", "upstream", id))
            git.checkout("upstream", true)

            println("[Feather] Switched to branch '${upstream.name}'...").toString()
        }

        createWorkspace(workspace, upstream)
    }

    public fun convertBranch(upstream: File, workspace: File, branchName: String) {
        workspace.delete()
        workspace.mkdirs()

        upstream.delete()
        upstream.mkdirs()

        shellRun(upstream) {
            git.gitCommand(listOf("init"))

            git.gitCommand(listOf("checkout", "--orphan", branchName))
        }

        createWorkspace(workspace, upstream)
    }

    public fun commit(workspace: File) {
        shellRun(workspace) {
            git.gitCommand(listOf("add", "*"))
            git.gitCommand(listOf("rebase", "--continue"))
        }
    }

    private fun createWorkspace(workspace: File, upstream: File) {
        workspace.delete()
        workspace.mkdirs()

        shellRun(workspace) {
            git.gitCommand(listOf("init"))

            git.gitCommand(listOf("remote", "add", "upstream", upstream.absolutePath))

            git.gitCommand(listOf("fetch", "upstream"))

            git.gitCommand(listOf("reset", "--hard", "upstream/upstream"))
            git.gitCommand(listOf("config", "commit.gpgsign", "false"))
        }
    }

    public fun applyPatches(workspace: File, patchFolder: File) {
        println("[Feather] Applying patches...")

        val patches = Arrays.stream(patchFolder.list()).filter { key: String ->
            key.lowercase(Locale.getDefault()).endsWith(".patch")
        }.sorted().collect(Collectors.toList())

        patches.forEach {
            println("Applying $it")

            val fail: Boolean = GitUtil.gitCommand(
                workspace, arrayOf(
                    "git", "am", "--3way",
                    "--ignore-whitespace", File(patchFolder, it).absolutePath
                )
            )

            if (fail) {
                println("[Feather] $it did not apply cleanly!")
                exitProcess(1)
            }
        }

        println("[Feather] Patches applied cleanly!")
    }

    public fun rebuildPatches(workspace: File, patchFolder: File) {
        shellRun(workspace) {
            git.gitCommand(listOf("format-patch", "--quiet", "-N", "-o", patchFolder.absolutePath, "upstream/upstream"))
        }
    }

    private fun moveFiles(from: File, to: File, skip: Predicate<File?>) {
        val files = from.listFiles() ?: return

        for (file in files) {
            if (skip.test(file)) {
                continue
            }

            if (file.isFile) {
                val target = File(to, file.name)
                target.parentFile.mkdirs()
                Files.move(file.toPath(), target.toPath())
            } else {
                moveFiles(file, File(to, file.name), skip)
            }
        }
    }

    private fun delete(file: File, skip: Predicate<File?>) {
        if (!file.exists()) return

        if (file.isDirectory) {
            val files = file.listFiles() ?: return

            for (key in files) delete(key, skip)
        }

        if (skip.test(file)) return

        file.delete()
    }

    private fun deleteEmptyDirectories(file: File, skip: Predicate<File?>) {
        if (!file.exists()) return

        if (file.isDirectory) {
            val files = file.listFiles() ?: return

            for (key in files) deleteEmptyDirectories(key, skip)

            if (files.isEmpty() && !skip.test(file)) {
                file.delete()
            }
        }
    }
}