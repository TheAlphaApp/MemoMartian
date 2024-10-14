package com.dzdexon.memomartian.ui.screens.edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dzdexon.memomartian.model.Note
import com.dzdexon.memomartian.model.Tag
import com.dzdexon.memomartian.repository.NotesRepository
import com.dzdexon.memomartian.utils.ALL_TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import javax.inject.Inject


data class EditScreenData(
    val isLoading: Boolean = false,
    val note: Note = Note(title = "", content = ""),
    val allTags: List<Tag> = listOf(),
    val selectedTags: List<Tag> = listOf(),
    val error: String? = null,
)

@HiltViewModel
class EditScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val notesRepository: NotesRepository,
) : ViewModel() {

    private val noteId: Long = checkNotNull(savedStateHandle[EditScreenDestination.noteIdArgs])

    private val _uiState =
        mutableStateOf(EditScreenData(isLoading = true))
    val uiState: State<EditScreenData> = _uiState
    private var saveJob: Job? = null

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
        val newNote = when (updateIt) {
            UpdateIt.TITLE -> title?.let { _uiState.value.note.copy(title = it) }
            UpdateIt.CONTENT -> content?.let { _uiState.value.note.copy(content = it) }
            UpdateIt.IMAGE -> image?.let { _uiState.value.note.copy(imageUri = it) }
        }

        newNote?.let { note ->
            _uiState.value = _uiState.value.copy(note = note)
            saveNoteWithDelay(1000)
        }
    }

    private fun saveNoteWithDelay(timeInMillis: Long) {
        saveJob?.cancel()
        saveJob = viewModelScope.launch {
            delay(timeInMillis) // Delay for 1 second (adjust as needed)
            _uiState.value = _uiState.value.copy(
                note = _uiState.value.note.copy(lastUpdate = OffsetDateTime.now())
            )
            notesRepository.updateNote(_uiState.value.note)
        }
    }

    fun updateTagInNote(tag: Tag, remove: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            if (remove) {
                notesRepository.removeTagFromNote(_uiState.value.note.noteId, tag.tagId)
            } else {
                notesRepository.addTagInNote(_uiState.value.note.noteId, tag.tagId)
            }
            getLatestData()
        }
    }

    fun deleteNote(callback: () -> Unit) {
        viewModelScope.launch {
            if (_uiState.value.note.noteId > 0)
                notesRepository.deleteNote(_uiState.value.note)
            callback()
        }
    }


    private fun validateTagString(tagString: String): Boolean {

        val isTagExist = _uiState.value.allTags.map {
            it.tagName.trim()
        }.contains(tagString) || tagString == ALL_TAG.tagName

        // Check if the tag string is blank
        if (tagString.isEmpty()) {
            // Set snackbar message for empty tag string

            return false
        }

        // Check if the tag already exists
        if (isTagExist) {
            // Set snackbar message for existing tag

            return false
        }

        // If everything is fine, return true
        return true
    }


    fun createNewTag(tagString: String) {
        val cleanedTagString = tagString.trim('#').trim()
        if (validateTagString(cleanedTagString))
            viewModelScope.launch {
                val id = notesRepository.createTag(Tag(tagName = cleanedTagString))
                if (id > 0) {
                    updateTagInNote(
                        Tag(
                            tagId = id,
                            tagName = cleanedTagString
                        )
                    ) // Tag creation successful
                }
                getLatestData()
            }
    }
}
