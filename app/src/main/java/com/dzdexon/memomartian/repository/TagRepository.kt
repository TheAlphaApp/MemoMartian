package com.dzdexon.memomartian.repository

import com.dzdexon.memomartian.data.entities.TagEntity
import kotlinx.coroutines.flow.Flow

interface TagRepository {
    fun getAllTagsStream(): Flow<List<TagEntity>>
    suspend fun createTag(tagEntity: TagEntity)
    suspend fun updateTag(tagEntity: TagEntity)
}