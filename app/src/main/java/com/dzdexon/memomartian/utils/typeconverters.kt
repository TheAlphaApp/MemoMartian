package com.dzdexon.memomartian.utils

import androidx.room.TypeConverter

class MyTypeConverters {
    @TypeConverter
    fun fromStringToList(value: String?) : List<String> {
        return value?.split(",")?.map { it.trim()} ?: emptyList()
    }

    @TypeConverter
    fun fromStringToList(list: List<String>): String {
        return list.joinToString(separator = ",")
    }
}
