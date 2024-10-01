package com.dzdexon.memomartian.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.dzdexon.memomartian.data.entities.NoteEntity
import com.dzdexon.memomartian.data.entities.NoteTagCrossRef
import com.dzdexon.memomartian.data.entities.NoteWithTags
import com.dzdexon.memomartian.data.entities.TagEntity
import com.dzdexon.memomartian.data.entities.TagWithNotes
import kotlinx.coroutines.flow.Flow


@Dao
interface NotesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNoteTagCrossRef(crossRef: NoteTagCrossRef)

    @Transaction
    @Query("SELECT * FROM notes WHERE noteId = :noteId LIMIT 1")
    fun getNoteWithTags(noteId: Long): Flow<NoteWithTags?>

    @Transaction
    @Query("SELECT * FROM tags WHERE tagId = :tagId LIMIT 1")
    fun getTagWithNotes(tagId: Long): Flow<TagWithNotes?>

    @Query("SELECT * FROM tags ORDER BY tagName ASC")
    fun getAllTags(): Flow<List<TagEntity>>

    @Transaction
    @Query("SELECT * FROM notes ORDER BY datetime(lastUpdate) DESC")
    fun getAllNotes(): Flow<List<NoteWithTags>>

    @Query("SELECT * FROM notes WHERE noteId = :id")
    fun getNote(id: Long) : Flow<NoteEntity?>

    @Transaction
    @Query("""
    SELECT * FROM notes 
    WHERE title LIKE '%' || :query || '%' 
    OR content LIKE '%' || :query || '%' 
    ORDER BY datetime(lastUpdate) DESC
""")
    fun searchNotes(query: String): Flow<List<NoteWithTags>>
    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Item into the database Room ignores the conflict.

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createNote(noteEntity: NoteEntity) : Long


    @Update
    suspend fun updateNote(noteEntity: NoteEntity)

    @Delete
    suspend fun deleteNote(noteEntity: NoteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createTag(tagEntity: TagEntity) : Long

    @Update
    suspend fun updateTag(tagEntity: TagEntity)

    @Delete
    suspend fun deleteTag(tagEntity: TagEntity)

    @Query("DELETE FROM NoteTagCrossRef WHERE noteId = :noteId AND tagId = :tagId")
    suspend fun removeTagFromNote(noteId: Long, tagId: Long)
}

//
//@Dao
//interface NotesDao {
//    @Query("SELECT * FROM notes ORDER BY datetime(lastUpdate) DESC")
//    fun getAllNotes(): Flow<List<NoteEntity>>
//
//    @Query("SELECT * FROM notes WHERE noteId = :id")
//    fun getNote(id: Int) : Flow<NoteEntity>
//
//    @Query("SELECT * FROM notes WHERE title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%' ORDER BY datetime(lastUpdate) DESC")
//    fun searchNotes(query: String): Flow<List<NoteEntity>>
//    // Specify the conflict strategy as IGNORE, when the user tries to add an
//    // existing Item into the database Room ignores the conflict.
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun createNote(noteEntity: NoteEntity)
//
//    @Update
//    suspend fun updateNote(noteEntity: NoteEntity)
//
//    @Delete
//    suspend fun deleteNote(noteEntity: NoteEntity)
//
//}