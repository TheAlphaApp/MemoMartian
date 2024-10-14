package com.dzdexon.memomartian.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dzdexon.memomartian.model.NoteWithTagsModel
import com.dzdexon.memomartian.model.Tag
import com.dzdexon.memomartian.model.TagWithNotesModel
import com.dzdexon.memomartian.repository.NotesRepository
import com.dzdexon.memomartian.utils.ALL_TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val notesRepository: NotesRepository,
) : ViewModel() {
    val stateFlowOfListOfNotes: StateFlow<List<NoteWithTagsModel>> = notesRepository
        .getAllNotesStream()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = listOf()
        )

    fun stateFlowTagWithNotes(tagId: Long): StateFlow<TagWithNotesModel?> {
        val flow = if (tagId == ALL_TAG.tagId) {
            notesRepository.getAllNotesStream().map { notesList ->
                TagWithNotesModel(
                    notes = notesList.map { it.note },
                    tag = ALL_TAG
                )
            }
        } else {
            notesRepository.getTagWithNotes(tagId)
        }

        return flow.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = null
        )
//        if (tagId == ALL_TAG.tagId)
//            return notesRepository.getAllNotesStream().stateIn(scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
//                initialValue = null).map { it ->
//                it.map { ss ->
//                    TagWithNotesModel(
//                        notes = ss.note,
//                        tag = ALL_TAG
//                    )
//                }
//            }
//        return notesRepository
//            .getTagWithNotes(tagId)
//            .stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
//                initialValue = null
//            )
    }

    val stateFlowOfListOfTags: StateFlow<List<Tag>> = notesRepository
        .getAllTagsStream()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = listOf()
        )

    fun createNewNote(callback: (Long) -> Unit) {
        viewModelScope.launch {
            try {
               val id =  notesRepository.createEmptyNote()
                callback(id)
            } catch (e: Exception) {
                // Handle the error (e.g., show a message) TODO:
            }
        }
    }
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}