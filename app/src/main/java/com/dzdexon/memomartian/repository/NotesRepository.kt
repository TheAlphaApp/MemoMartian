package com.dzdexon.memomartian.repository

import com.dzdexon.memomartian.data.entities.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    fun getAllNotesStream(): Flow<List<NoteEntity>>
    fun getNoteStream(id: Int): Flow<NoteEntity?>
    suspend fun searchNotes(query: String): Flow<List<NoteEntity>>

    suspend fun createNote(noteEntity: NoteEntity)
    suspend fun updateNote(noteEntity: NoteEntity)
}