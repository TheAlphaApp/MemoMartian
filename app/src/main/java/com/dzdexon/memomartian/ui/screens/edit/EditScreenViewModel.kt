package com.dzdexon.memomartian.ui.screens.edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dzdexon.memomartian.model.Note
import com.dzdexon.memomartian.model.Tag
import com.dzdexon.memomartian.repository.NotesRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.OffsetDateTime

class EditScreenViewModel(
    savedStateHandle: SavedStateHandle,
    private val notesRepository: NotesRepository
) : ViewModel() {
    private val emptyNote = Note(
        id = 0,
        title = "",
        content = "",
        tags = emptyList(),
        lastUpdate = null,
        imageUri = null
    )
    var note by mutableStateOf(emptyNote)
        private set
    private val noteId: Int = checkNotNull(savedStateHandle[EditScreenDestination.noteIdArgs])

    init {
        viewModelScope.launch {
            note = notesRepository.getNoteStream(noteId)
                .filterNotNull()
                .first()
        }
    }



    fun updateUiState(note: Note) {
           this.note = note
    }

    fun updateTagInNote(tag: Tag, remove: Boolean = false) {
        if (remove) {
            if (note.tags.contains(tag.id)) {
                val tags = note.tags.toMutableList()
                tags.remove(tag.id)
                note = note.copy(
                    tags = tags
                )
            }
        } else {
            val tags = note.tags.toMutableList()
            tags.add(tag.id)
            note = note.copy(
                tags = tags
            )
        }

    }

    suspend fun updateNote() {
        if (validateInput(this.note)) {
            this.note = this.note.copy(lastUpdate = OffsetDateTime.now())
            notesRepository.updateNote(this.note)
        }
    }

    fun validateInput(uiState: Note = this.note): Boolean {
        return with(uiState) {
            title.isNotBlank() && content.isNotBlank()
        }
    }
}
