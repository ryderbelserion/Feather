package other

import org.gradle.api.Action
import org.gradle.plugin.devel.GradlePluginDevelopmentExtension
import org.gradle.plugin.devel.PluginDeclaration

fun GradlePluginDevelopmentExtension.configurePlugin(prefix: String, action: Action<PluginDeclaration>) {
    plugins.register("feather-$prefix") {
        id = "com.ryderbelserion.feather.$prefix"
        displayName = "feather $prefix"
        tags.set(listOf("kotlin", "utility"))
        action.execute(this)
    }
}