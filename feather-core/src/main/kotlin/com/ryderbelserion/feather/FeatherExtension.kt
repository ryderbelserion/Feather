package com.ryderbelserion.feather

import com.ryderbelserion.feather.records.VendorConfigure
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.javadoc.Javadoc
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.repositories
import org.gradle.kotlin.dsl.withType
import org.gradle.language.jvm.tasks.ProcessResources
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

public abstract class FeatherExtension(private val project: Project) {

    public fun configureKotlin(block: VendorConfigure.() -> Unit): Unit = with(project) {
        val configure = VendorConfigure().apply(block)

        afterEvaluate {
            val version = configure.javaVersion

            extensions.configure<KotlinJvmProjectExtension> {
                if (configure.kotlinExplicit) {
                    explicitApi()
                }

                jvmToolchain {
                    it.languageVersion.set(JavaLanguageVersion.of(version))
                    it.vendor.set(configure.javaSource)
                }
            }

            tasks.apply {
                withType<KotlinCompile>().all {
                    it.kotlinOptions {
                        jvmTarget = "$version"

                        javaParameters = true
                    }
                }
            }
        }
    }

    public fun configureJava(block: VendorConfigure.() -> Unit): Unit = with(project) {
        val configure = VendorConfigure().apply(block)

        afterEvaluate {
            val version = configure.javaVersion

            extensions.configure<JavaPluginExtension> {
                toolchain {
                    it.languageVersion.set(JavaLanguageVersion.of(version))
                    it.vendor.set(configure.javaSource)
                }
            }

            tasks.apply {
                withType<JavaCompile> {
                    options.encoding = Charsets.UTF_8.name()
                    options.release.set(version)
                }

                withType<Javadoc> {
                    options.encoding = Charsets.UTF_8.name()
                }

                withType<ProcessResources> {
                    filteringCharset = Charsets.UTF_8.name()
                }
            }
        }
    }

    public fun repository(url: String): Unit = with(project) {
        repositories {
            maven(url)
        }
    }
}