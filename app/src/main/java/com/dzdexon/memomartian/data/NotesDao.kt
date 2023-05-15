package com.dzdexon.memomartian.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dzdexon.memomartian.model.Note
import com.dzdexon.memomartian.model.Tag
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {
    @Query("SELECT * FROM notes ORDER BY datetime(lastUpdate) DESC")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNote(id: Int) : Flow<Note>
    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Item into the database Room ignores the conflict.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Query("SELECT * FROM tags ORDER BY tagName ASC")
    fun getAllTags() : Flow<List<Tag>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createTag(tag: Tag)

    @Update
    suspend fun updateTag(tag: Tag)
}