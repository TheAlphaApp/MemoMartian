package com.dzdexon.memomartian.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dzdexon.memomartian.model.Note
import com.dzdexon.memomartian.repository.NotesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(notesRepository: NotesRepository) : ViewModel() {

    val stateFlowOfListOfNotes: StateFlow<List<Note>> = notesRepository
        .getAllNotesStream()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = listOf()
        )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}