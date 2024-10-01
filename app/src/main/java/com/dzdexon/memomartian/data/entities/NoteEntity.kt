package com.dzdexon.memomartian.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dzdexon.memomartian.model.Note
import java.time.OffsetDateTime

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val noteId: Long = 0,
    val title: String,
    val content: String,
    val lastUpdate: OffsetDateTime? = null,
    val imageUri: String? = null
)

fun NoteEntity.asExternalModel(): Note {
    return Note(
        noteId = this.noteId,
        title = this.title,
        content = this.content,
        lastUpdate = this.lastUpdate,
        imageUri = this.imageUri
    )
}