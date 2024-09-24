package com.dzdexon.memomartian.repository

import com.dzdexon.memomartian.data.TagDao
import com.dzdexon.memomartian.data.entities.TagEntity
import com.dzdexon.memomartian.data.entities.asExternalModel
import com.dzdexon.memomartian.model.Tag
import com.dzdexon.memomartian.model.asEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OfflineTagRepository(private val tagDao: TagDao) : TagRepository {
    override fun getAllTagsStream(): Flow<List<Tag>> =
        tagDao.getAllTags().map { it.map(TagEntity::asExternalModel) }

    override suspend fun createTag(tag: Tag): Long {
        tagDao.createTag(tag.asEntity()).also {
            return it
        }
    }

    override suspend fun updateTag(tag: Tag) = tagDao.updateTag(tag.asEntity())

    override suspend fun deleteTag(tag: Tag) {
        tagDao.deleteTag(tag.asEntity())
    }
}