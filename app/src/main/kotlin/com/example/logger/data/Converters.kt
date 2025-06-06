package com.example.logger.data

import androidx.room.TypeConverter
import kotlin.time.Duration

class Converters {
    @TypeConverter
    fun fromDuration(value: String): Duration? {
        if (value == "null")
            return null
        return Duration.parse(value)
    }
    @TypeConverter
    fun durationToString(duration: Duration?): String {
        return duration.toString()
    }
}