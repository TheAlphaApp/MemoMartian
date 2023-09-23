package com.dzdexon.memomartian.repository

import com.dzdexon.memomartian.data.NotesDao
import com.dzdexon.memomartian.data.entities.NoteEntity
import kotlinx.coroutines.flow.Flow

class OfflineNotesRepository(private val notesDao: NotesDao): NotesRepository {
    override fun getAllNotesStream(): Flow<List<NoteEntity>>  = notesDao.getAllNotes()
    override fun getNoteStream(id: Int): Flow<NoteEntity?> = notesDao.getNote(id)
    override suspend fun searchNotes(query: String):Flow<List<NoteEntity>> = notesDao.searchNotes(query)

    override suspend fun createNote(noteEntity: NoteEntity) = notesDao.createNote(noteEntity)
    override suspend fun updateNote(noteEntity: NoteEntity)  = notesDao.updateNote(noteEntity)

}