package com.example.commonwallet.database

import androidx.room.TypeConverter
import java.time.*

class Converters {
    @TypeConverter
    fun timeToLong(time: ZonedDateTime?): Long? {
        return time?.toInstant()?.toEpochMilli()
    }

    @TypeConverter
    fun longToTime(timestamp: Long?): ZonedDateTime? {
        val instant = Instant.ofEpochMilli(timestamp!!)
        return ZonedDateTime.ofInstant(instant, ZoneId.systemDefault())
    }
}