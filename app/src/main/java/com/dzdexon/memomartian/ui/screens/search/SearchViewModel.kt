package com.dzdexon.memomartian.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dzdexon.memomartian.model.Note
import com.dzdexon.memomartian.repository.NotesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchViewModel(
    private val notesRepository: NotesRepository
) : ViewModel() {
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()


    //TODO: [_isSearching] is not implemented
    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes = _notes

    private fun searchNote() {
        viewModelScope.launch {
            notesRepository
                .searchNotes(
                    searchText.value
                ).collectLatest { searchedNotes ->
                    _notes.value = searchedNotes
                }
        }
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
        _isSearching.value = true
        searchNote()
        _isSearching.value = false
    }
}