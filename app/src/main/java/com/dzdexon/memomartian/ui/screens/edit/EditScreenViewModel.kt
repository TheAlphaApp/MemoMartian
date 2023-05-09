package com.dzdexon.memomartian.ui.screens.edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dzdexon.memomartian.model.NoteUiState
import com.dzdexon.memomartian.model.toNote
import com.dzdexon.memomartian.model.toNoteUiState
import com.dzdexon.memomartian.repository.NotesRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EditScreenViewModel(
    savedStateHandle: SavedStateHandle,
    private val notesRepository: NotesRepository
) : ViewModel() {

    private val noteId: Int = checkNotNull(savedStateHandle[EditScreenDestination.noteIdArgs])

    init {
        viewModelScope.launch {
            noteUiState = notesRepository.getNoteStream(noteId)
                .filterNotNull()
                .first()
                .toNoteUiState(true)
        }
    }

    var noteUiState by mutableStateOf(NoteUiState())
        private set

    fun updateUiState(note: NoteUiState) {
        noteUiState = note.copy(isValid = validateInput(note))
        validateInput(noteUiState)
    }

    fun updateTagInNote(tag: String, remove: Boolean = false) {
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

    suspend fun updateNote() {
        if (validateInput()) {
            notesRepository.updateNote(noteUiState.toNote())
        }
    }

    private fun validateInput(uiState: NoteUiState = noteUiState): Boolean {
        return with(uiState) {
            title.isNotBlank() && content.isNotBlank()
        }
    }
}
