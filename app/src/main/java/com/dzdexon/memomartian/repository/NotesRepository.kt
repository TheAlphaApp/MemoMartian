package com.dzdexon.memomartian.repository

import com.dzdexon.memomartian.model.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    fun getAllNotesStream(): Flow<List<Note>>
    fun getNoteStream(id: Int): Flow<Note?>
    suspend fun searchNotes(query: String): Flow<List<Note>>

    suspend fun createNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(note: Note)
}