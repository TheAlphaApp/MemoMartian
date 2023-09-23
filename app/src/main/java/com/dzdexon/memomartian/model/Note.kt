package com.dzdexon.memomartian.model
import com.dzdexon.memomartian.data.entities.NoteEntity
import java.time.OffsetDateTime

data class Note(
    val id: Int = 0,
    val title: String,
    val content: String,
    val tags: List<Int>,
    val lastUpdate: OffsetDateTime? = null,
    val imageUri: String? = null
)
fun Note.asEntity() = NoteEntity(
    id = id,
    title = title,
    content = content,
    tags = tags,
    lastUpdate = lastUpdate,
    imageUri = imageUri
)

/**
 * Extension function to convert [NoteEntity] to [NoteUiState]*/
fun Note.toNoteUiState(isValid: Boolean): NoteUiState = NoteUiState(
    id = id,
    title = title,
    content = content,
    tags = tags,
    isValid = isValid,
    lastUpdate = lastUpdate,
    imageUri = imageUri
)


