package com.dzdexon.memomartian.repository

import com.dzdexon.memomartian.model.Tag
import kotlinx.coroutines.flow.Flow

interface TagRepository {
    fun getAllTagsStream(): Flow<List<Tag>>
    suspend fun createTag(tag: Tag) : Long
    suspend fun updateTag(tag: Tag)
    suspend fun deleteTag(tag: Tag)
}