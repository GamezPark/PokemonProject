package com.pokemon.project.home.data.service.network

import com.squareup.moshi.*
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MoshiUTCDateUtil : JsonAdapter<Date>() {

    var dateFormat: DateFormat? = null

    init {
        dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    }

    @FromJson
    @Synchronized
    @Throws(ParseException::class)
    override fun fromJson(reader: JsonReader): Date? {
        return dateFormat?.parse(reader.readJsonValue().toString())
    }

    @ToJson
    @Synchronized
    override fun toJson(writer: JsonWriter, value: Date?) {
        writer.value(dateFormat?.format(value))
    }
}