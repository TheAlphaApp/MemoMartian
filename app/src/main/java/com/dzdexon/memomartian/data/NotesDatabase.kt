package com.dzdexon.memomartian.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dzdexon.memomartian.data.entities.NoteEntity
import com.dzdexon.memomartian.data.entities.NoteTagCrossRef
import com.dzdexon.memomartian.data.entities.TagEntity
import com.dzdexon.memomartian.utils.MyTypeConverters


/**
 * Database class with a singleton INSTANCE object.
 */
@TypeConverters(MyTypeConverters::class)
@Database(
    entities = [NoteEntity::class, TagEntity::class, NoteTagCrossRef::class],
    version = 4,
    exportSchema = false
)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
}