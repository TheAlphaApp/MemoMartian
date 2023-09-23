package com.dzdexon.memomartian.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dzdexon.memomartian.data.entities.TagEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {

    @Query("SELECT * FROM tags ORDER BY tagName ASC")
    fun getAllTags() : Flow<List<TagEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createTag(tagEntity: TagEntity)

    @Update
    suspend fun updateTag(tagEntity: TagEntity)
}