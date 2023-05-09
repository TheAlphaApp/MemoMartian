package com.dzdexon.memomartian.ui.screens.create

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.dzdexon.memomartian.model.NoteUiState
import com.dzdexon.memomartian.model.toNote
import com.dzdexon.memomartian.repository.NotesRepository

class CreateScreenViewModel(private val notesRepository: NotesRepository) : ViewModel() {
    var noteUiState by mutableStateOf(NoteUiState())
        private set

    fun updateUiState(note: NoteUiState) {
        noteUiState = note.copy(isValid = validateInput(note))
    }
    fun updateTagInNewNote(tag: String, remove: Boolean = false) {
        if (remove) {
            if (noteUiState.tags.contains(tag)) {
                val tags = noteUiState.tags.toMutableList()
                tags.remove(tag)
                noteUiState = noteUiState.copy(
                    tags = tags
                )
            }
        } else {
            val tags = noteUiState.tags.toMutableList()
            tags.add(tag)
            noteUiState = noteUiState.copy(
                tags = tags
            )
        }

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

