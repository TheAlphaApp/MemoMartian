package com.dzdexon.memomartian.ui.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dzdexon.memomartian.model.Note
import com.dzdexon.memomartian.repository.NotesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn

class DetailScreenViewModel(
    savedStateHandle: SavedStateHandle,
    notesRepository: NotesRepository
) : ViewModel() {
    private val emptyNote = Note(
        id = 0,
        title = "",
        content = "",
        tags = emptyList(),
        lastUpdate = null,
        imageUri = null
    )
    private val noteId: Int = checkNotNull(savedStateHandle[DetailScreenDestination.noteIdArgs])

    /**
     * Holds the item details ui state. The data is retrieved from [NotesRepository] and mapped to
     * the UI state.
     */
    val uiState: StateFlow<Note> = notesRepository
        .getNoteStream(noteId)
        .filterNotNull()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = emptyNote
        )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}
