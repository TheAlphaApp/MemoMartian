package com.dzdexon.memomartian.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val content: String,
    val tags: List<Int>,
    val lastUpdate: OffsetDateTime? = null,
    val imageUri: String? = null
)