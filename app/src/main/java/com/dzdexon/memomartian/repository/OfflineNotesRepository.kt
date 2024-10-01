package com.dzdexon.memomartian.repository

import com.dzdexon.memomartian.data.NotesDao
import com.dzdexon.memomartian.data.entities.NoteEntity
import com.dzdexon.memomartian.data.entities.NoteTagCrossRef
import com.dzdexon.memomartian.data.entities.TagEntity
import com.dzdexon.memomartian.data.entities.asExternalModel
import com.dzdexon.memomartian.model.Note
import com.dzdexon.memomartian.model.NoteWithTagsModel
import com.dzdexon.memomartian.model.Tag
import com.dzdexon.memomartian.model.TagWithNotesModel
import com.dzdexon.memomartian.model.asEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OfflineNotesRepository(private val notesDao: NotesDao) : NotesRepository {
    override fun getAllNotesStream(): Flow<List<NoteWithTagsModel>> =
        notesDao.getAllNotes().map {
            it.map { noteWithTags ->
                NoteWithTagsModel(
                    note = noteWithTags.note.asExternalModel(),
                    tags = noteWithTags.tags.map(TagEntity::asExternalModel),
                )
            }
        }

    override fun getNoteStream(id: Long): Flow<Note?> =
        notesDao.getNote(id).map { it?.asExternalModel() }

    override fun getNoteWithTags(noteId: Long): Flow<NoteWithTagsModel?> {
        return notesDao.getNoteWithTags(noteId).map { noteWithTags ->
            noteWithTags?.let {
                NoteWithTagsModel(
                    note = it.note.asExternalModel(),
                    tags = it.tags.map(TagEntity::asExternalModel)
                )
            }
        }

    }

    override fun getTagWithNotes(tagId: Long): Flow<TagWithNotesModel?> {
        return notesDao.getTagWithNotes(tagId).map { tagWithNotes ->
            tagWithNotes?.let {
                TagWithNotesModel(
                    tag = it.tag.asExternalModel(),
                    notes = it.notes.map(NoteEntity::asExternalModel)
                )
            }
        }

    }

    override fun searchNotes(query: String): Flow<List<NoteWithTagsModel>> {
        return notesDao.searchNotes(query).map {
            it.map { noteWithTags ->
                NoteWithTagsModel(
                    note = noteWithTags.note.asExternalModel(),
                    tags = noteWithTags.tags.map(TagEntity::asExternalModel),
                )
            }
        }
    }


    override suspend fun createEmptyNote(): Long {
        val noteId: Long = notesDao.createNote(
            noteEntity = NoteEntity(
                title = "",
                content = "",
                lastUpdate = null,
                imageUri = null
            )
        )
        return noteId
    }

    override suspend fun addTagInNote(noteId: Long, tagId: Long) {
        notesDao.insertNoteTagCrossRef(NoteTagCrossRef(noteId, tagId))
    }

    override suspend fun updateNote(note: Note) {
        val tempNote: NoteEntity = note.asEntity()
        notesDao.updateNote(tempNote)

    }

    override suspend fun deleteNote(note: Note) {
        notesDao.deleteNote(note.asEntity())
    }

    override suspend fun removeTagFromNote(noteId: Long, tagId: Long) {
        notesDao.removeTagFromNote(noteId, tagId)
    }
    override fun getAllTagsStream(): Flow<List<Tag>> =
        notesDao.getAllTags().map { it.map(TagEntity::asExternalModel) }

    override suspend fun createTag(tag: Tag): Long {
        notesDao.createTag(tag.asEntity()).also {
            return it
        }
    }

    override suspend fun updateTag(tag: Tag) = notesDao.updateTag(tag.asEntity())

    override suspend fun deleteTag(tag: Tag) {
        notesDao.deleteTag(tag.asEntity())
    }
}