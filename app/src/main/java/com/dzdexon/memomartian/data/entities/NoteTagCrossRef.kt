package com.dzdexon.memomartian.data.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.Junction
import androidx.room.Relation

@Entity(
    primaryKeys = ["noteId", "tagId"],
    foreignKeys = [
        ForeignKey(entity = NoteEntity::class, parentColumns = ["noteId"], childColumns = ["noteId"]),
        ForeignKey(entity = TagEntity::class, parentColumns = ["tagId"], childColumns = ["tagId"])
    ],
    indices = [Index("noteId"), Index("tagId")]
)
data class NoteTagCrossRef(
    val noteId: Long,
    val tagId: Long
)



data class NoteWithTags(
    @Embedded val note: NoteEntity,
    @Relation(
        parentColumn = "noteId",
        entityColumn = "tagId",
        associateBy = Junction(NoteTagCrossRef::class)
    )
    val tags: List<TagEntity>
)

data class TagWithNotes(
    @Embedded val tag: TagEntity,
    @Relation(
        parentColumn = "tagId",
        entityColumn = "noteId",
        associateBy = Junction(NoteTagCrossRef::class)
    )
    val notes: List<NoteEntity>
)