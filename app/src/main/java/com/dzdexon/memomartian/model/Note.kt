package com.dzdexon.memomartian.model
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.time.OffsetDateTime

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val content: String,
    val tags: List<Int>,
    val lastUpdate: OffsetDateTime? = null,
)