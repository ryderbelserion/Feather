package com.ryderbelserion.feather.webhook.builders

import com.ryderbelserion.feather.webhook.types.components.Image
import com.ryderbelserion.feather.webhook.types.embeds.Embed
import com.ryderbelserion.feather.webhook.types.embeds.Field
import com.ryderbelserion.feather.webhook.types.embeds.data.Author
import com.ryderbelserion.feather.webhook.types.embeds.data.Footer
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

    fun fields(builder: FieldsBuilder.() -> Unit) {
        this.fields = FieldsBuilder().apply(builder).build()
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

private fun Color.toInt(): Int {
    val red = red shl 16 and 0xFF0000
    val green = green shl 8 and 0x00FF00
    val blue = blue and 0x0000FF

    return red or green or blue
}