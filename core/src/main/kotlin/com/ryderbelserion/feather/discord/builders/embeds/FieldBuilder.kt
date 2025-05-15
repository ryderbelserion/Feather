package com.ryderbelserion.feather.discord.builders.embeds

import com.ryderbelserion.feather.discord.data.embeds.Field

class FieldBuilder {

    private val fields: MutableList<Field> = mutableListOf()

    fun field(name: String, value: String, inline: Boolean = false) {
        this.fields.add(Field(name, value, inline))
    }

    internal fun build() = this.fields.toList()
}