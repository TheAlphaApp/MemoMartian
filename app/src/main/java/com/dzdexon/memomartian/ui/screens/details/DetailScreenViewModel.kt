package com.dzdexon.memomartian.ui.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dzdexon.memomartian.model.NoteWithTagsModel
import com.dzdexon.memomartian.navigation.NOTE_ID_KEY
import com.dzdexon.memomartian.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    notesRepository: NotesRepository
) : ViewModel() {

    private val noteId: Long = checkNotNull(savedStateHandle[NOTE_ID_KEY])

    /**
     * Holds the item details ui state. The data is retrieved from [NotesRepository] and mapped to
     * the UI state.
     */
    val uiState: StateFlow<NoteWithTagsModel?> = notesRepository
        .getNoteWithTags(noteId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = null
        )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}
