package com.dzdexon.memomartian.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dzdexon.memomartian.model.Note
import com.dzdexon.memomartian.model.Tag
import com.dzdexon.memomartian.utils.MyTypeConverters


/**
 * Database class with a singleton INSTANCE object.
 */
@TypeConverters(MyTypeConverters::class)
@Database(entities = [Note::class, Tag::class], version = 2, exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
    abstract fun tagDao(): TagDao
    companion object {
        @Volatile
        private var Instance: NotesDatabase? = null

        fun getDatabase(context: Context): NotesDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, NotesDatabase::class.java, "notes_database")
                /**
                 * Setting this option in your app's database builder means that Room
                 * permanently deletes all data from the tables in your database when it
                 * attempts to perform a migration with no defined migration path.
                 */
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}