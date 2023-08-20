package com.dzdexon.memomartian

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.dzdexon.memomartian.ui.screens.create.CreateScreenViewModel
import com.dzdexon.memomartian.ui.screens.details.DetailScreenViewModel
import com.dzdexon.memomartian.ui.screens.edit.EditScreenViewModel
import com.dzdexon.memomartian.ui.screens.home.HomeViewModel
import android.app.Application
import com.dzdexon.memomartian.ui.screens.managetags.TagManageViewModel
import com.dzdexon.memomartian.ui.screens.search.SearchViewModel

/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */

object AppViewModelProvider {
    val Factory = viewModelFactory {
        //Initializer for HomeViewModel
        initializer {
            HomeViewModel(
                notesApplication().container.notesRepository
            )
        }
        initializer {
            EditScreenViewModel(
                this.createSavedStateHandle(),
                notesApplication().container.notesRepository
            )
        }

        initializer {
            CreateScreenViewModel(
                notesApplication().container.notesRepository
            )
        }
        initializer {
            DetailScreenViewModel(
                this.createSavedStateHandle(),
                notesApplication().container.notesRepository
            )
        }
        initializer {
            TagManageViewModel(
                notesApplication().container.tagRepository
            )
        }
        initializer {
            SearchViewModel(
                notesApplication().container.notesRepository
            )
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [NotesApplication].
 */

fun CreationExtras.notesApplication() : NotesApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NotesApplication)