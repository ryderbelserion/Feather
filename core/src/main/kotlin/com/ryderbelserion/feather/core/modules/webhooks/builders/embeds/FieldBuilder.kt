package com.ryderbelserion.feather.core.modules.webhooks.builders.embeds

import com.ryderbelserion.feather.core.modules.webhooks.data.embeds.Field

class FieldBuilder {

    private val fields: MutableList<Field> = mutableListOf()

    fun field(name: String, value: String, inline: Boolean = false) {
        this.fields.add(Field(name, value, inline))
    }

    internal fun build() = this.fields.toList()
}