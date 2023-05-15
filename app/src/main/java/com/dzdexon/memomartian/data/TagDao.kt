package com.dzdexon.memomartian.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dzdexon.memomartian.model.Tag
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {

    @Query("SELECT * FROM tags ORDER BY tagName ASC")
    fun getAllTags() : Flow<List<Tag>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createTag(tag: Tag)

    @Update
    suspend fun updateTag(tag: Tag)
}