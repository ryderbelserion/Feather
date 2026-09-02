package com.ryderbelserion.feather.patcher.api.objects

import java.io.OutputStream

class Command(private val builder: ProcessBuilder) {

    private var input: OutputStream = EmptyOutput
    private var error: OutputStream = EmptyOutput

    fun run(): Int {
        val process = this.builder.start()

        val input = process.inputStream
        val error = process.errorStream
        val buffer = ByteArray(1000)

        return runCatching {
            while (process.isAlive) {
                if (input.available() > 0) {
                    this.input.write(buffer, 0, input.read(buffer))
                }

                if (error.available() > 0) {
                    this.error.write(buffer, 0, error.read(buffer))
                }

                Thread.sleep(1)
            }

            this.input.write(input.readBytes())
            this.error.write(error.readBytes())

            return process.waitFor()
        }.getOrDefault(-1)
    }

    object EmptyOutput : OutputStream() {
        override fun write(value: Int) {}
    }
}