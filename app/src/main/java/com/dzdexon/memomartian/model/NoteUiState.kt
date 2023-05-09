package com.dzdexon.memomartian.model


data class NoteUiState(
    val id: Int = 0,
    val title: String = "",
    val content: String = "",
    val tags: List<String> = emptyList(),
    val isValid: Boolean = false,
)

/**
 * Extension function to convert [Note] to [NoteUiState]*/
fun Note.toNoteUiState(isValid: Boolean): NoteUiState = NoteUiState(
    id = id,
    title = title,
    content = content,
    tags = tags,
    isValid = isValid
)

/**
 * Extension function to convert [NoteUiState] to [Note]*/
fun NoteUiState.toNote(): Note = Note(
    id = id,
    title = title,
    content = content,
    tags = tags
)