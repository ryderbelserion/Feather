package com.ryderbelserion.feather

import com.ryderbelserion.feather.records.VendorConfigure
import com.ryderbelserion.feather.records.PublishConfigure
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.publish.PublishingExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

public abstract class FeatherExtension(private val project: Project) {

    public fun configureKotlin(block: VendorConfigure.() -> Unit): Unit = with(project) {
        val configure = VendorConfigure().apply(block)

        afterEvaluate {
            val version = JavaLanguageVersion.of(configure.javaVersion)

            extensions.configure<KotlinJvmProjectExtension> {
                if (configure.kotlinExplicit) {
                    explicitApi()
                }

                jvmToolchain {
                    it.languageVersion.set(version)
                    it.vendor.set(configure.javaSource)
                }
            }
        }
    }

    public fun configureJava(block: VendorConfigure.() -> Unit): Unit = with(project) {
        val configure = VendorConfigure().apply(block)

        afterEvaluate {
            val version = JavaLanguageVersion.of(configure.javaVersion)

            extensions.configure<JavaPluginExtension> {
                toolchain {
                    it.languageVersion.set(version)
                    it.vendor.set(configure.javaSource)
                }
            }
        }
    }

    public fun publish(block: PublishConfigure.() -> Unit): Unit = with(project) {
        val configure = PublishConfigure(this).apply(block)

        project.extensions.configure<PublishingExtension> {
            publications {

            }
        }
    }
}