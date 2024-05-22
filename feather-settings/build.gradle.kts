plugins {
    `feather-parent`
}

gradlePlugin {
    plugins.all {
        implementationClass = "com.ryderbelserion.feather.FeatherSettings"
    }
}