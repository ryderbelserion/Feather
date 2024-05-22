plugins {
    `feather-parent`
}

gradlePlugin {
    plugins.all {
        implementationClass = "com.ryderbelserion.feather.FeatherCore"
    }
}

dependencies {
    implementation(libs.gradle.kotlin.dsl)
}