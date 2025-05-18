package com.ryderbelserion.feather.core.discord.builders.embeds

import com.ryderbelserion.feather.core.discord.data.components.Image
import com.ryderbelserion.feather.core.discord.data.embeds.Author
import com.ryderbelserion.feather.core.discord.data.embeds.Embed
import com.ryderbelserion.feather.core.discord.data.embeds.Field
import com.ryderbelserion.feather.core.discord.data.embeds.Footer
import com.ryderbelserion.feather.core.toInt
import java.awt.Color
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class EmbedBuilder {

    private var title: String? = null
    private var description: String? = null
    private var url: String? = null
    private var color: Int? = null

    private var author: Author? = null

    private var footer: Footer? = null
    private var timestamp: String = ""

    private var thumbnail: Image? = null
    private var image: Image? = null

    private var fields: List<Field>? = null

    fun title(title: String) {
        this.title = title
    }

    fun description(description: String) {
        this.description = description
    }

    fun url(url: String) {
        this.url = url
    }

    fun timestamp(date: LocalDateTime) {
        this.timestamp = date.format(DateTimeFormatter.ISO_DATE_TIME)
    }

    fun color(color: Color) {
        this.color = color.toInt()
    }

    fun color(color: String) {
        this.color = color.replace("#", "").toInt(16)
    }

    fun footer(text: String, icon: String? = null) {
        this.footer = Footer(text, Image(icon))
    }

    fun author(name: String, icon: String? = null) {
        this.author = Author(name, Image(icon))
    }

    fun thumbnail(url: String) {
        this.thumbnail = Image(url)
    }

    fun image(url: String) {
        this.image = Image(url)
    }

    fun fields(builder: FieldBuilder.() -> Unit) {
        this.fields = FieldBuilder().apply(builder).build()
    }

    internal fun build() = Embed(
        this.title,
        this.description,
        this.color,
        this.image,
        this.thumbnail,
        this.author,
        this.footer,
        this.fields
    )
}