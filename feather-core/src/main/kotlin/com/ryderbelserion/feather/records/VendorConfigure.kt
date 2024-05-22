package com.ryderbelserion.feather.records

import org.gradle.jvm.toolchain.JvmVendorSpec

public class VendorConfigure {

    internal var javaSource: JvmVendorSpec = JvmVendorSpec.AMAZON
    internal var kotlinExplicit: Boolean = false
    internal var javaVersion: Int = -1

    public fun javaSource(javaSource: JvmVendorSpec) {
        this.javaSource = javaSource
    }

    public fun javaVersion(version: Int) {
        this.javaVersion = version
    }

    public fun isExplicitApi() {
        this.kotlinExplicit = true
    }
}