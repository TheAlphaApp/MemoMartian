package com.dzdexon.memomartian.ui.screens.managetags

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dzdexon.memomartian.model.Tag
import com.dzdexon.memomartian.repository.NotesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class TagManageViewModel(private val notesRepository: NotesRepository) : ViewModel() {


    var tagList: StateFlow<List<Tag>> = notesRepository
        .getAllTagsStream()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = listOf()
        )
        private set

    suspend fun updateTag(tag: Tag, tagString: String) {
        if (validateTagString(tagString)) {
            notesRepository.updateTag(tag.copy(tagName = tagString))
        }

    }

    private fun validateTagString(tagString: String): Boolean {
        val isTagExist = tagList.value.map {
            it.tagName.trim()
        }.contains(tagString) || tagString.trim() == "All"
        return tagString.isNotBlank() && tagString.isNotEmpty() && !isTagExist
    }

    suspend fun createNewTag(tagString: String) {
        if (validateTagString(tagString)) {
            notesRepository.createTag(Tag(tagName = tagString.trim()))
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

