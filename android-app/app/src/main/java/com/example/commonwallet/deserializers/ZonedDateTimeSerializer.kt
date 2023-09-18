package com.example.commonwallet.deserializers

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class ZonedDateTimeSerializer:JsonSerializer<ZonedDateTime> {
    override fun serialize(
        src: ZonedDateTime?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src?.format(DateTimeFormatter.ISO_INSTANT))
    }
}