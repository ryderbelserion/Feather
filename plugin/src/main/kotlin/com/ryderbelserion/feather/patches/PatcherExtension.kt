package com.ryderbelserion.feather.patches

import java.nio.file.Path

abstract class PatcherExtension {

    private var datafolder: Path? = null
    private var workspace: String = ""
    private var sha: String = ""
    private var url: String = ""
    private var autoupdate: Boolean = false

    private var branch: String = ""

    /**
     * Sets the root folder.
     */
    fun datafolder(datafolder: Path): PatcherExtension {
        this.datafolder = datafolder

        return this
    }

    /**
     * Sets the directory where we clone the repository.
     */
    fun workspace(workspace: String) {
        this.workspace = workspace
    }

    /**
     * Sets the commit hash
     */
    fun sha(sha: String) {
        this.sha = sha
    }

    /**
     * Gets the commit hash
     */
    fun sha(): String {
        return this.sha
    }

    /**
     * Sets the branch name
     */
    fun branch(name: String) {
        this.branch = name
    }

    /**
     * Gets the branch name
     */
    fun branch(): String {
        return this.branch
    }

    fun autoupdate(autoupdate: Boolean) {
        this.autoupdate = autoupdate
    }

    fun autoupdate(): Boolean {
        return this.autoupdate
    }

    /**
     * Sets the .git url
     */
    fun url(url: String) {
        this.url = url
    }

    /**
     * Gets the .git url
     */
    fun url(): String {
        return this.url
    }

    internal fun build(): Extension {
        return Extension(
            Plugin(this.datafolder, this.workspace),
            Upstream(this.sha, this.autoupdate, this.url, this.branch)
        )
    }

    data class Extension(
        val plugin: Plugin,
        val upstream: Upstream,
    )

    data class Plugin(
        val path: Path?,
        val workspace: String
    )

    data class Upstream(
        val sha: String,
        val autoUpstream: Boolean,
        val url: String,
        val branch: String
    )
}