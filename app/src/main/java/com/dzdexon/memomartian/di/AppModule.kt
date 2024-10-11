package com.dzdexon.memomartian.di

import android.content.Context
import androidx.room.Room
import com.dzdexon.memomartian.data.NotesDao
import com.dzdexon.memomartian.data.NotesDatabase
import com.dzdexon.memomartian.repository.NotesRepository
import com.dzdexon.memomartian.repository.OfflineNotesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providesLocalDatabase(@ApplicationContext context: Context) : NotesDatabase {
        return Room.databaseBuilder(context, NotesDatabase::class.java, "notes_database")
            /**
             * Setting this option in your app's database builder means that Room
             * permanently deletes all data from the tables in your database when it
             * attempts to perform a migration with no defined migration path.
             */
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideNotesDao(database: NotesDatabase): NotesDao {
        return database.notesDao()
    }
    @Singleton
    @Provides
    fun provideNotesRepository(notesDao: NotesDao): NotesRepository {
        return OfflineNotesRepository(notesDao)
    }

}