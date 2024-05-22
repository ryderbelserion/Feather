package com.ryderbelserion.feather.enums

import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.maven

private typealias repo = RepositoryHandler.(url: String, project: Project) -> Unit

private val maven: repo = { url, _ -> maven(url) }

public enum class Repository(public val url: String, public val block: repo = maven) {
    // CrazyCrew
    CrazyCrewSnapshots("https://repo.crazycrew.us/snapshots"),
    CrazyCrewReleases("https://repo.crazycrew.us/releases"),

    // PaperMC
    Paper("https://papermc.io/repo/repository/maven-public"),

    // Mojang
    MinecraftLibraries("https://libraries.minecraft.net"),

    // Other
    Jitpack("https://jitpack.io")
}