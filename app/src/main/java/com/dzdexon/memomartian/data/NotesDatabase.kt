package com.dzdexon.memomartian.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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
@Database(entities = [NoteEntity::class, TagEntity::class, NoteTagCrossRef::class], version = 4, exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
    companion object {
        @Volatile
        private var INSTANCE: NotesDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): NotesDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(context, NotesDatabase::class.java, "notes_database")
                /**
                 * Setting this option in your app's database builder means that Room
                 * permanently deletes all data from the tables in your database when it
                 * attempts to perform a migration with no defined migration path.
                 */
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }

    override fun close() {
        INSTANCE?.close()
        INSTANCE = null
    }


}