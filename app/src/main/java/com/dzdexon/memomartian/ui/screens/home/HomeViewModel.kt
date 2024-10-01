package com.dzdexon.memomartian.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dzdexon.memomartian.model.Note
import com.dzdexon.memomartian.model.NoteWithTagsModel
import com.dzdexon.memomartian.model.Tag
import com.dzdexon.memomartian.model.TagWithNotesModel
import com.dzdexon.memomartian.repository.NotesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.OffsetDateTime

class HomeViewModel(
    private val notesRepository: NotesRepository,
) : ViewModel() {
    private val dummyNote = Note(
        noteId = 0,
        title = "Hello there",
        content = "room",
        lastUpdate = OffsetDateTime.now(),
        imageUri = null
    )


    val stateFlowOfListOfNotes: StateFlow<List<NoteWithTagsModel>> = notesRepository
        .getAllNotesStream()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = listOf()
        )

    fun stateFlowTagWithNotes(tagId: Long): StateFlow<TagWithNotesModel?> {
        return notesRepository
            .getTagWithNotes(tagId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = null
            )
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

//    private fun createDummyData() {
//        if (stateFlowOfListOfNotes.value.isEmpty() || stateFlowOfListOfTags.value.isEmpty())
//            viewModelScope.launch {
//                notesRepository.createNote(dummyData)
//                dummyTagData.forEach {
//                tagRepository.createTag(it)
//            }
//        }
//    }

//    init {
//        createDummyData()
//    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}