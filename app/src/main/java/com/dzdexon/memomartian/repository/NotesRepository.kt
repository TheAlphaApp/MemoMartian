package com.dzdexon.memomartian.repository

import com.dzdexon.memomartian.model.Note
import com.dzdexon.memomartian.model.Tag
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    fun getAllNotesStream(): Flow<List<Note>>
    fun getNoteStream(id: Int): Flow<Note?>
    suspend fun createNote(note: Note)
    suspend fun updateNote(note: Note)

    fun getAllTagsStream(): Flow<List<Tag>>
    suspend fun createTag(tag: Tag)
    suspend fun updateTag(tag: Tag)

}