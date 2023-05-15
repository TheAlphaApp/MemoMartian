package com.dzdexon.memomartian.repository

import com.dzdexon.memomartian.data.TagDao
import com.dzdexon.memomartian.model.Tag
import kotlinx.coroutines.flow.Flow

class OfflineTagRepository(private val tagDao: TagDao): TagRepository {
    override fun getAllTagsStream(): Flow<List<Tag>> = tagDao.getAllTags()
    override suspend fun createTag(tag: Tag) = tagDao.createTag(tag)
    override suspend fun updateTag(tag: Tag) = tagDao.updateTag(tag)
}