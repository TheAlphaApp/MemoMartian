package com.dzdexon.memomartian.model

import com.dzdexon.memomartian.data.entities.NoteEntity
import java.time.OffsetDateTime

data class NoteUiState(
    val id: Int = 0,
    val title: String = "",
    val content: String = "",
    val tags: List<Int> = emptyList<Int>(),
    val isValid: Boolean = false,
    val lastUpdate: OffsetDateTime? = null,
    val imageUri: String? = null
)



/**
 * Extension function to convert [NoteUiState] to [NoteEntity]*/
fun NoteUiState.toNote(): Note = Note(
    id = id,
    title = title,
    content = content,
    tags = tags,
    lastUpdate = lastUpdate,
    imageUri = imageUri
)