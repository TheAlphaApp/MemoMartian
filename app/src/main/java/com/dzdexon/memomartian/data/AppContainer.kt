package com.dzdexon.memomartian.data

import android.content.Context
import com.dzdexon.memomartian.repository.NotesRepository
import com.dzdexon.memomartian.repository.OfflineNotesRepository
import com.dzdexon.memomartian.repository.OfflineTagRepository
import com.dzdexon.memomartian.repository.TagRepository

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val notesRepository: NotesRepository
    val tagRepository: TagRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineNotesRepository]
 */

class AppDataContainer(private val context: Context): AppContainer {
    /*
    * Implementation for [NotesRepository]*/
    override val notesRepository: NotesRepository by lazy {
        OfflineNotesRepository(NotesDatabase.getDatabase(context).notesDao())
    }
    override val tagRepository: TagRepository by lazy {
        OfflineTagRepository(NotesDatabase.getDatabase(context).tagDao())
    }
}