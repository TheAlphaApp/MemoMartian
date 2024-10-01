package com.dzdexon.memomartian.repository

import com.dzdexon.memomartian.model.Note
import com.dzdexon.memomartian.model.NoteWithTagsModel
import com.dzdexon.memomartian.model.Tag
import com.dzdexon.memomartian.model.TagWithNotesModel
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    suspend fun addTagInNote(noteId: Long, tagId: Long)
    fun getAllNotesStream(): Flow<List<NoteWithTagsModel>>
    fun getNoteStream(id: Long): Flow<Note?>
    fun searchNotes(query: String): Flow<List<NoteWithTagsModel>>
    suspend fun createEmptyNote(): Long
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(note: Note)
    fun getAllTagsStream(): Flow<List<Tag>>
    fun getNoteWithTags(noteId: Long): Flow<NoteWithTagsModel?>
    fun getTagWithNotes(tagId: Long): Flow<TagWithNotesModel?>
    suspend fun createTag(tag: Tag) : Long
    suspend fun updateTag(tag: Tag)
    suspend fun deleteTag(tag: Tag)
    suspend fun removeTagFromNote(noteId: Long, tagId: Long)
}