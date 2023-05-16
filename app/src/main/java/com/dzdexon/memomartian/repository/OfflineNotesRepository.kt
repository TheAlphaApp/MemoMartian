package com.dzdexon.memomartian.repository

import com.dzdexon.memomartian.data.NotesDao
import com.dzdexon.memomartian.model.Note
import kotlinx.coroutines.flow.Flow

class OfflineNotesRepository(private val notesDao: NotesDao): NotesRepository {
    override fun getAllNotesStream(): Flow<List<Note>>  = notesDao.getAllNotes()
    override fun getNoteStream(id: Int): Flow<Note?> = notesDao.getNote(id)
    override suspend fun searchNotes(query: String):Flow<List<Note>> = notesDao.searchNotes(query)

    override suspend fun createNote(note: Note) = notesDao.createNote(note)
    override suspend fun updateNote(note: Note)  = notesDao.updateNote(note)

}