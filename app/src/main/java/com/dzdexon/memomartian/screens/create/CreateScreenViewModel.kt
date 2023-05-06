package com.dzdexon.memomartian.screens.create

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.dzdexon.memomartian.data.NotesRepository
import com.dzdexon.memomartian.model.Note

class CreateScreenViewModel(private val notesRepository: NotesRepository) : ViewModel() {
    var noteUiState by mutableStateOf(NoteUiState())
        private set

    fun updateUiState(note: NoteUiState) {
        noteUiState = note.copy(isValid = validateInput(note))
        validateInput(noteUiState)
    }

        suspend fun saveNote() {
        if (validateInput()) {
            notesRepository.createNote(noteUiState.toNote())
        }
    }
    private fun validateInput(uiState: NoteUiState = noteUiState): Boolean {
        return with(uiState) {
            title.isNotBlank() && content.isNotBlank()
        }
    }
}

data class NoteUiState(
    val id: Int = 0,
    val title: String = "",
    val content: String = "",
    val tags: List<String> = listOf(),
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
