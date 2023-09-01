package com.ryderbelserion.feather.utils

import com.lordcodes.turtle.shellRun
import java.io.File
import java.util.*
import java.util.stream.Collectors
import kotlin.system.exitProcess

class FileUtil {

    fun getUpstream(directory: File, workspace: File, toggle: Boolean, url: String, sha: String, branch: String) {
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

    fun commit(workspace: File) {
        shellRun(workspace) {
            git.gitCommand(listOf("git add *"))
            git.gitCommand(listOf("git rebase --continue"))
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

    fun applyPatches(workspace: File, patchFolder: File) {
        println("[Feather] Applying patches...")

        val patches = Arrays.stream(patchFolder.list()).filter { s: String ->
            s.lowercase(Locale.getDefault()).endsWith(".patch")
        }.sorted().collect(Collectors.toList())

        patches.forEach {
            println("Applying $it")

            val fail: Boolean = GitUtils().gitCommand(
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

    fun rebuildPatches(workspace: File, patchFolder: File) {
        shellRun(workspace) {
            git.gitCommand(listOf("format-patch", "--quiet", "-N", "-o", patchFolder.absolutePath, "upstream/upstream"))
        }
    }
}