package com.dzdexon.memomartian.ui.screens.create

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.dzdexon.memomartian.model.NoteUiState
import com.dzdexon.memomartian.data.entities.TagEntity
import com.dzdexon.memomartian.model.toNote
import com.dzdexon.memomartian.repository.NotesRepository
import java.time.OffsetDateTime

class CreateScreenViewModel(private val notesRepository: NotesRepository) : ViewModel() {
    var noteUiState by mutableStateOf(NoteUiState())
        private set

    fun updateUiState(note: NoteUiState) {
        noteUiState = note.copy(isValid = validateInput(note))
    }
    fun updateTagInNewNote(tagEntity: TagEntity, remove: Boolean = false) {
        if (remove) {
            if (noteUiState.tags.contains(tagEntity.id)) {
                val tags = noteUiState.tags.toMutableList()
                tags.remove(tagEntity.id)
                noteUiState = noteUiState.copy(
                    tags = tags
                )
            }
        } else {
            val tags = noteUiState.tags.toMutableList()
            tags.add(tagEntity.id)
            noteUiState = noteUiState.copy(
                tags = tags
            )
        }

    }
    suspend fun saveNote() {
        if (validateInput()) {
            noteUiState = noteUiState.copy(
                lastUpdate = OffsetDateTime.now()
            )
            notesRepository.createNote(noteUiState.toNote())
        }
    }

    private fun validateInput(uiState: NoteUiState = noteUiState): Boolean {
        return with(uiState) {
            title.isNotBlank() && content.isNotBlank()
        }
    }
}

