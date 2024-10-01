package com.dzdexon.memomartian.model

import com.dzdexon.memomartian.data.entities.NoteEntity
import java.time.OffsetDateTime

data class Note(
    val noteId: Long = 0,
    val title: String,
    val content: String,
    val lastUpdate: OffsetDateTime? = null,
    val imageUri: String? = null
)

fun Note.asEntity(): NoteEntity {
    return NoteEntity(
        noteId = this.noteId,
        title = this.title,
        content = this.content,
        lastUpdate = this.lastUpdate,
        imageUri = this.imageUri
    )
}


data class NoteWithTagsModel(
    val note: Note,
    val tags: List<Tag>
)

data class TagWithNotesModel(
    val tag: Tag,
    val notes: List<Note>
)



