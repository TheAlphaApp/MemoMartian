package com.dzdexon.memomartian.ui.screens.edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dzdexon.memomartian.model.Note
import com.dzdexon.memomartian.model.Tag
import com.dzdexon.memomartian.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.OffsetDateTime


data class EditScreenData(
    val isLoading: Boolean = false,
    val note: Note = Note(title = "", content = ""),
    val allTags: List<Tag> = listOf(),
    val selectedTags: List<Tag> = listOf(),
    val error: String? = null,
)


class EditScreenViewModel(
    savedStateHandle: SavedStateHandle,
    private val notesRepository: NotesRepository,
) : ViewModel() {
    private val noteId: Long = checkNotNull(savedStateHandle[EditScreenDestination.noteIdArgs])

    private val _uiState =
        mutableStateOf(EditScreenData(isLoading = true))
    val uiState: State<EditScreenData> = _uiState
    private fun getLatestData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Fetch all tags and the note with tags sequentially
                val tagList = notesRepository.getAllTagsStream().first()
                val noteWithTags = notesRepository.getNoteWithTags(noteId).first()

                // Update the UI state
                _uiState.value = if (noteWithTags != null) {
                    _uiState.value.copy(
                        note = noteWithTags.note,
                        allTags = tagList,
                        selectedTags = noteWithTags.tags,
                        error = null,
                        isLoading = false
                    )

                } else {
                    _uiState.value.copy(
                        error = "Note not found"
                    )
                }
            } catch (e: Exception) {
                // Handle errors and update state accordingly
                _uiState.value = _uiState.value.copy(error = "Error loading data: ${e.message}")
            }
        }
    }

    init {
        getLatestData()
    }

    fun saveNote() {
        viewModelScope.launch(Dispatchers.IO) {
            _saveNote()
        }
    }

    private suspend fun _saveNote() {
        val updatedNote = _uiState.value.note.copy(lastUpdate = OffsetDateTime.now())
        _uiState.value = _uiState.value.copy(
            note = updatedNote,
            isLoading = true
        )
        notesRepository.updateNote(_uiState.value.note)
        _uiState.value = _uiState.value.copy(isLoading = false)
    }

    //    fun updateUiState(noteWithTagsModel: NoteWithTagsModel) {
//        this.noteWithTagsModel = noteWithTagsModel
//        saveNoteJob?.cancel()
//
//        // Launch a new coroutine with a delay for debouncing
//        saveNoteJob = viewModelScope.launch {
//            delay(500) // Wait for 500ms
//            notesRepository.updateNote(noteWithTagsModel.note, noteWithTagsModel.tags.map { it.tagId }) // Save note after the user stops typing
//        }
//    }
//    fun updateUiState(noteWithTagsModel: NoteWithTagsModel) {
//        this.noteWithTagsModel = noteWithTagsModel
//    }

    enum class UpdateIt {
        TITLE,
        CONTENT,
        IMAGE
    }

    fun updateUI(
        title: String? = null,
        content: String? = null,
        image: String? = null,
        updateIt: UpdateIt,
    ) {
        when (updateIt) {
            UpdateIt.TITLE -> {
                title?.let {
                    _uiState.value = _uiState.value.copy(
                        note = _uiState.value.note.copy(title = title)
                    )
                }

            }

            UpdateIt.CONTENT -> {

                content?.let {
                    _uiState.value = _uiState.value.copy(
                        note = _uiState.value.note.copy(content = content)
                    )
                }
            }

            UpdateIt.IMAGE -> {
                image?.let {
                    _uiState.value = _uiState.value.copy(
                        note = _uiState.value.note.copy(imageUri = image)
                    )
                }
            }
        }
    }

    fun updateTagInNote(tag: Tag, remove: Boolean = false) {
        viewModelScope.launch {
            _updateTagInNote(tag, remove)
        }
    }

    private suspend fun _updateTagInNote(tag: Tag, remove: Boolean = false) {
        // Update the tags based on the `remove` flag
        if (remove) {
            notesRepository.removeTagFromNote(_uiState.value.note.noteId, tag.tagId)
        } else {
            notesRepository.addTagInNote(_uiState.value.note.noteId, tag.tagId)
        }
        _saveNote()

        getLatestData()

    }

    //    fun validateInput(note: Note): Boolean {
//        return note.title.isNotBlank() && note.content.isNotBlank()
//    }
    fun deleteNote() {
        viewModelScope.launch {
            if (_uiState.value.note.noteId > 0)
                notesRepository.deleteNote(_uiState.value.note)
        }
    }


    private fun validateTagString(tagString: String): Boolean {
        val isTagExist = _uiState.value.allTags.map {
            it.tagName.trim()
        }.contains(tagString) || tagString.trim() == "All"
        return tagString.isNotBlank() && tagString.isNotEmpty() && !isTagExist
    }

    fun createNewTag(tagString: String) {
        if (validateTagString(tagString))
            viewModelScope.launch {
                val id = notesRepository.createTag(Tag(tagName = tagString.trim()))
                if (id > 0) {
                    updateTagInNote(
                        Tag(
                            tagId = id,
                            tagName = tagString.trim()
                        )
                    ) // Tag creation successful
                }
                getLatestData()
            }

    }

//    companion object {
//        private const val TIMEOUT_MILLIS = 5_000L
//    }
}
