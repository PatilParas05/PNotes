

package com.example.pnote

import androidx.room.TypeConverter
import java.util.Date

class DateConverters { // Class is fine as a container
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}