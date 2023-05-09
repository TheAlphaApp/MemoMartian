package com.dzdexon.memomartian.utils

import androidx.room.TypeConverter

class MyTypeConverters {
    @TypeConverter
    fun fromStringToListOfInt(value: String?): List<Int> {
        if (value?.isNotBlank() == true ) {
            return value.split(",").map {
                it.toInt()
            }
        } else {
            return emptyList<Int>()
        }

    }

    @TypeConverter
    fun fromListOfIntToString(list: List<Int>): String {
        return list.joinToString(separator = ",")
    }
}
