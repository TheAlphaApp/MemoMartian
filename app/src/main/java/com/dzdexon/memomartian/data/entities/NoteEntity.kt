package com.dzdexon.memomartian.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dzdexon.memomartian.model.Note
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

fun NoteEntity.asExternalModel(): Note {
    return Note(
        id = this.id,
        title = this.title,
        content = this.content,
        tags = this.tags,
        lastUpdate = this.lastUpdate,
        imageUri = this.imageUri
    )
}