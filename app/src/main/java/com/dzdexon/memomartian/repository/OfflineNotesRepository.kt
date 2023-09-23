package com.dzdexon.memomartian.repository

import com.dzdexon.memomartian.data.NotesDao
import com.dzdexon.memomartian.data.entities.NoteEntity
import com.dzdexon.memomartian.data.entities.asExternalModel
import com.dzdexon.memomartian.model.Note
import com.dzdexon.memomartian.model.asEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OfflineNotesRepository(private val notesDao: NotesDao) : NotesRepository {
    override fun getAllNotesStream(): Flow<List<Note>> =
        notesDao.getAllNotes().map { it.map(NoteEntity::asExternalModel) }

    override fun getNoteStream(id: Int): Flow<Note?> =
        notesDao.getNote(id).map { it.asExternalModel() }

    override suspend fun searchNotes(query: String): Flow<List<Note>> =
        notesDao.searchNotes(query).map { it.map(NoteEntity::asExternalModel) }

    override suspend fun createNote(note: Note) = notesDao.createNote(note.asEntity())

    override suspend fun updateNote(note: Note) = notesDao.updateNote(note.asEntity())

}