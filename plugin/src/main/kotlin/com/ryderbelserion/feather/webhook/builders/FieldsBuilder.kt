package com.ryderbelserion.feather.webhook.builders

import com.ryderbelserion.feather.webhook.types.embeds.Field

class FieldsBuilder {

    private val fields: MutableList<Field> = mutableListOf()

    fun field(name: String, value: String, inline: Boolean = false) {
        this.fields.add(Field(name, value, inline))
    }

    internal fun build() = this.fields.toList()
}