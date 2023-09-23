package com.dzdexon.memomartian.repository

import com.dzdexon.memomartian.data.TagDao
import com.dzdexon.memomartian.data.entities.TagEntity
import kotlinx.coroutines.flow.Flow

class OfflineTagRepository(private val tagDao: TagDao): TagRepository {
    override fun getAllTagsStream(): Flow<List<TagEntity>> = tagDao.getAllTags()
    override suspend fun createTag(tagEntity: TagEntity) = tagDao.createTag(tagEntity)
    override suspend fun updateTag(tagEntity: TagEntity) = tagDao.updateTag(tagEntity)
}