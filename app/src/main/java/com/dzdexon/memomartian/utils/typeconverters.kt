package com.dzdexon.memomartian.utils

import androidx.room.TypeConverter
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

object MyTypeConverters {
    @TypeConverter
    fun fromStringToListOfInt(value: String?): List<Int> {
        return if (value?.isNotBlank() == true ) {
            value.split(",").map {
                it.toInt()
            }
        } else {
            emptyList<Int>()
        }

    }

    @TypeConverter
    fun fromListOfIntToString(list: List<Int>): String {
        return list.joinToString(separator = ",")
    }

    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @TypeConverter
    @JvmStatic
    fun toOffsetDateTime(value: String?): OffsetDateTime? {
        return value?.let {
            return formatter.parse(value, OffsetDateTime::from)
        }
    }

    @TypeConverter
    @JvmStatic
    fun fromOffsetDateTime(date: OffsetDateTime?): String? {
        return date?.format(formatter)
    }
}
