package com.dzdexon.memomartian.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dzdexon.memomartian.model.Note
import com.dzdexon.memomartian.model.Tag
import com.dzdexon.memomartian.repository.NotesRepository
import com.dzdexon.memomartian.repository.TagRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.OffsetDateTime

class HomeViewModel(
    private val notesRepository: NotesRepository,
    private val tagRepository: TagRepository
) : ViewModel() {
    private val dummyData = Note(
        id = 0,
        title = "Hello there",
        content = "room",
        tags = listOf(451, 582, 203),
        lastUpdate = OffsetDateTime.now(),
        imageUri = null
    )
    private val dummyTagData: List<Tag> = listOf(
        Tag(451, "Hours"),
        Tag(582, "Minutes"),
        Tag(203, "Seconds"),
        Tag(656, "Kalyan"),
    )

    val stateFlowOfListOfNotes: StateFlow<List<Note>> = notesRepository
        .getAllNotesStream()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = listOf()
        )
    private val stateFlowOfListOfTags: StateFlow<List<Tag>> = tagRepository
        .getAllTagsStream()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = listOf()
        )





    private fun createDummyData() {
        if (stateFlowOfListOfNotes.value.isEmpty() || stateFlowOfListOfTags.value.isEmpty())
            viewModelScope.launch {
                notesRepository.createNote(dummyData)
                dummyTagData.forEach {
                tagRepository.createTag(it)
            }
        }
    }

    init {
        createDummyData()
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}