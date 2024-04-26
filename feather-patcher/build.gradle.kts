plugins {
    `publish-plugin`
}

gradlePlugin {
    plugins.all {
        description = "A gradle plugin handling patch files"
        implementationClass = "com.ryderbelserion.feather.patcher.FeatherPatcher"
    }
}