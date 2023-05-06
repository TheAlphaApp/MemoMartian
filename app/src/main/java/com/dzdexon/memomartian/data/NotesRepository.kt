package com.dzdexon.memomartian.data

import com.dzdexon.memomartian.model.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    fun getAllNotesStream(): Flow<List<Note>>
    fun getNoteStream(id: Int): Flow<Note?>
    suspend fun createNote(note: Note)
    suspend fun updateNote(note: Note)
}