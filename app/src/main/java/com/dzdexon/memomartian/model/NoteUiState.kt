package com.dzdexon.memomartian.model

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
 * Extension function to convert [Note] to [NoteUiState]*/
fun Note.toNoteUiState(isValid: Boolean): NoteUiState = NoteUiState(
    id = id,
    title = title,
    content = content,
    tags = tags,
    isValid = isValid,
    lastUpdate = lastUpdate,
    imageUri = imageUri
)

/**
 * Extension function to convert [NoteUiState] to [Note]*/
fun NoteUiState.toNote(): Note = Note(
    id = id,
    title = title,
    content = content,
    tags = tags,
    lastUpdate = lastUpdate,
    imageUri = imageUri
)