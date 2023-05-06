package com.dzdexon.memomartian.screens.edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dzdexon.memomartian.data.NotesRepository
import com.dzdexon.memomartian.screens.create.NoteUiState
import com.dzdexon.memomartian.screens.create.toNote
import com.dzdexon.memomartian.screens.create.toNoteUiState
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EditScreenViewModel(
    savedStateHandle: SavedStateHandle,
    private val notesRepository: NotesRepository) : ViewModel() {

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
