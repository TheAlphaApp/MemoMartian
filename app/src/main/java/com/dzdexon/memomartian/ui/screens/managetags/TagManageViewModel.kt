package com.dzdexon.memomartian.ui.screens.managetags

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dzdexon.memomartian.model.Tag
import com.dzdexon.memomartian.repository.TagRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class TagManageViewModel(private val tagRepository: TagRepository) : ViewModel() {
    var tagState: StateFlow<TagState> = tagRepository
        .getAllTagsStream().map {
            TagState(it)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = TagState()
        )
        private set

    suspend fun updateTag(tag: Tag, tagString: String) {
        if (validateTagString(tagString)) {
            tagRepository.updateTag(tag.copy(tagName = tagString))
        }

    }

    private fun validateTagString(tagString: String): Boolean {
        val isTagExist = tagState.value.tagList.map {
            it.tagName.trim()
        }.contains(tagString) || tagString.trim() == "All"
        return tagString.isNotBlank() && tagString.isNotEmpty() && !isTagExist
    }

    suspend fun createNewTag(tagString: String) {
        if (validateTagString(tagString)) {
            tagRepository.createTag(Tag(tagName = tagString.trim()))
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class TagState(val tagList: List<Tag> = listOf())