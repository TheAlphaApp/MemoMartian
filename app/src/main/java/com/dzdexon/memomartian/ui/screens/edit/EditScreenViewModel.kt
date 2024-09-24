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
import com.dzdexon.memomartian.repository.TagRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okhttp3.internal.toImmutableList
import java.time.OffsetDateTime

class EditScreenViewModel(
    savedStateHandle: SavedStateHandle,
    private val notesRepository: NotesRepository,
    private val tagRepository: TagRepository,
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


    var tagList: StateFlow<List<Tag>> = tagRepository
        .getAllTagsStream()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = listOf()
        )
        private set

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

    suspend fun updateTagInNote(tag: Tag, remove: Boolean = false) {
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
        notesRepository.updateNote(this.note)
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

    fun deleteNote() {
        viewModelScope.launch {
            notesRepository.deleteNote(note)
        }
    }


    private fun validateTagString(tagString: String): Boolean {
        val isTagExist = tagList.value.map {
            it.tagName.trim()
        }.contains(tagString) || tagString.trim() == "All"
        return tagString.isNotBlank() && tagString.isNotEmpty() && !isTagExist
    }

    fun createNewTag(tagString: String): Boolean {
        if (validateTagString(tagString)) {
            val isCompleted = viewModelScope.launch {
                tagRepository.createTag(Tag(tagName = tagString.trim())).also { id ->
                    val tags = note.tags.toMutableList()
                    tags.add(id.toInt())
                    note = note.copy(
                        tags = tags.toImmutableList()
                    )
                    notesRepository.updateNote(note)
                }
            }.isCompleted

            return isCompleted
        }
        return false
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}
