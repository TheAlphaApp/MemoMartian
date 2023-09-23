package com.dzdexon.memomartian.repository

import com.dzdexon.memomartian.data.entities.Tag
import kotlinx.coroutines.flow.Flow

interface TagRepository {
    fun getAllTagsStream(): Flow<List<Tag>>
    suspend fun createTag(tag: Tag)
    suspend fun updateTag(tag: Tag)
}