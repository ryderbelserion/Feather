package com.ryderbelserion.feather.patcher.entry.tasks

import org.gradle.api.DefaultTask

abstract class BaseTask : DefaultTask() {

    open fun init() {}

}