package com.dzdexon.memomartian.ui.screens.create

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.dzdexon.memomartian.model.Note
import com.dzdexon.memomartian.model.Tag
import com.dzdexon.memomartian.repository.NotesRepository
import okhttp3.internal.toImmutableList
import java.time.OffsetDateTime

class CreateScreenViewModel(private val notesRepository: NotesRepository) : ViewModel() {
    private val emptyNote = Note(
        id = 0,
        title = "",
        content = "",
        tags = emptyList(),
        lastUpdate = null,
        imageUri = null
    )
    var note: Note by mutableStateOf(emptyNote)
        private set

    fun updateUiState(note: Note) {
            this.note = note
    }

    fun updateTagInNewNote(tag: Tag, remove: Boolean = false) {

        if (remove) {
            if (note.tags.contains(tag.id)) {
                val tags = note.tags.toMutableList()
                tags.remove(tag.id)
                note = note.copy(
                    tags = tags.toImmutableList()
                )
            }
        } else {
            val tags = note.tags.toMutableList()
            tags.add(tag.id)
            note = note.copy(
                tags = tags.toImmutableList()
            )
        }


    }

    suspend fun saveNote() {
        if (validateInput(note)) {
            note = note.copy(
                lastUpdate = OffsetDateTime.now()
            )
            notesRepository.createNote(note)
        }
    }

   fun validateInput(uiState: Note = this.note): Boolean {
        return with(uiState) {
            title.isNotBlank() && content.isNotBlank()
        }
    }
}

