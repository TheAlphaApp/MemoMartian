//package com.dzdexon.memomartian.ui.screens.create
//
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateListOf
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.dzdexon.memomartian.data.entities.NoteEntity
//import com.dzdexon.memomartian.data.entities.NoteWithTags
//import com.dzdexon.memomartian.model.Note
//import com.dzdexon.memomartian.model.Tag
//import com.dzdexon.memomartian.model.asEntity
//import com.dzdexon.memomartian.repository.NotesRepository
//import com.dzdexon.memomartian.ui.screens.home.HomeViewModel
//import com.dzdexon.memomartian.ui.screens.home.HomeViewModel.Companion
//import kotlinx.coroutines.flow.SharingStarted
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.stateIn
//import kotlinx.coroutines.launch
//import okhttp3.internal.toImmutableList
//import java.time.OffsetDateTime
//
//class CreateScreenViewModel(
//    private val notesRepository: NotesRepository,
//) : ViewModel() {
//
//    var note: NoteWithTags? by mutableStateOf(null)
//        private set
//
//    var tagList: StateFlow<List<Tag>> = notesRepository
//        .getAllTagsStream()
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
//            initialValue = listOf()
//        )
//        private set
//
//    fun updateUiState(note: Note, tagIds: List<Long>) {
//        this.note = NoteWithTags(note = note.asEntity(), tags = )
//    }
//
//    fun updateTagInNewNote(tag: Tag, remove: Boolean = false) {
//
//        if (remove) {
//            if (note.tags.contains(tag.tagId)) {
//                val tags = note.tags.toMutableList()
//                tags.remove(tag.tagId)
//                note = note.copy(
//                    tags = tags.toImmutableList()
//                )
//            }
//        } else {
//            val tags = note.tags.toMutableList()
//            tags.add(tag.tagId)
//            note = note.copy(
//                tags = tags.toImmutableList()
//            )
//        }
//    }
//
//    suspend fun saveNote() {
//        if (validateInput(note)) {
//            note = note.copy(
//                lastUpdate = OffsetDateTime.now()
//            )
//            notesRepository.createNote(note)
//        }
//    }
//
//    fun validateInput(uiState: Note = this.note): Boolean {
//        return with(uiState) {
//            title.isNotBlank() && content.isNotBlank()
//        }
//    }
//
//    private fun validateTagString(tagString: String): Boolean {
//        val isTagExist = tagList.value.map {
//            it.tagName.trim()
//        }.contains(tagString.trim())
//        val isTagNameIsAll = tagString.trim() == "All"
//        return tagString.isNotBlank() && tagString.isNotEmpty() && !isTagExist && !isTagNameIsAll
//    }
//
//    fun createNewTagInsideANote(tagString: String) : Boolean {
//        if (validateTagString(tagString)) {
//            val isCompleted = viewModelScope.launch {
//                notesRepository.createTag(Tag(tagName = tagString.trim())).also { id ->
//                    notesRepository.
//                    val tags = note.tags.toMutableList()
//                    tags.add(id.toInt())
//                    note = note.copy(
//                        tags = tags.toImmutableList()
//                    )
//                    notesRepository.updateNote(note)
//                }
//            }.isCompleted
//            return isCompleted
//        }
//        return false
//    }
//
//    companion object {
//        private const val TIMEOUT_MILLIS = 5_000L
//    }
//
//}
//
