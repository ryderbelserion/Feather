plugins {
    `feather-parent`
}

gradlePlugin {
    plugins.all {
        implementationClass = "com.ryderbelserion.feather.FeatherPatcher"
    }
}

dependencies {
    implementation("com.lordcodes.turtle:turtle:0.10.0")
}