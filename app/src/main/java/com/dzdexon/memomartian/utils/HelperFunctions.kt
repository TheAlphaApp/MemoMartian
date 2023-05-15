package com.dzdexon.memomartian.utils

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

object HelperFunctions {
    fun formatOffsetDateTime(offsetDateTime: OffsetDateTime?): String? {
        if (offsetDateTime != null) {
            val now = OffsetDateTime.now() // get the current date and time
            val difference = ChronoUnit.SECONDS.between(
                offsetDateTime,
                now
            ) // calculate the difference in seconds between the two dates
            val formatter = DateTimeFormatter.ofPattern("dd MMMM") // define the date format

            return when {
                difference < 60 -> "just now"
                difference < 3600 -> "${difference / 60} minutes ago"
                difference < 86400 -> "${difference / 3600} hours ago"
                difference < 172800 -> "yesterday"
                difference < 604800 -> "${difference / 86400} days ago"
                offsetDateTime.year == now.year -> offsetDateTime.format(formatter) // format the date as "dd MMMM" if it's in the current year
                else -> offsetDateTime.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")) // format the date as "dd MMMM yyyy" if it's not in the current year
            }
        } else {
            return null
        }
    }
}