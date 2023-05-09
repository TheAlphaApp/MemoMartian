package com.dzdexon.memomartian.repository

import com.dzdexon.memomartian.data.NotesDao
import com.dzdexon.memomartian.model.Note
import com.dzdexon.memomartian.model.Tag
import kotlinx.coroutines.flow.Flow

class OfflineNotesRepository(private val notesDao: NotesDao): NotesRepository {
    override fun getAllNotesStream(): Flow<List<Note>>  = notesDao.getAllNotes()
    override fun getNoteStream(id: Int): Flow<Note?> = notesDao.getNote(id)
    override suspend fun createNote(note: Note) = notesDao.createNote(note)
    override suspend fun updateNote(note: Note)  = notesDao.updateNote(note)
    override fun getAllTagsStream(): Flow<List<Tag>> = notesDao.getAllTags()
    override suspend fun createTag(tag: Tag) = notesDao.createTag(tag)
    override suspend fun updateTag(tag: Tag) = notesDao.updateTag(tag)

}