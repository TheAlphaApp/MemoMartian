package com.dzdexon.memomartian.navigation

const val NOTE_ID_KEY = "note_id"
sealed class NemoRoutes(val route: String) {
    data object HomeScreen: NemoRoutes(route = "home")
    data object DetailScreen: NemoRoutes(route = "detail/{$NOTE_ID_KEY}") {
       fun withNoteId(id: String): String {
            return this.route.replace("{$NOTE_ID_KEY}", id)
        }
    }
    data object EditScreen: NemoRoutes(route = "edit/{$NOTE_ID_KEY}") {
        fun withNoteId(id: String): String {
            return this.route.replace("{$NOTE_ID_KEY}", id)
        }
    }
    data object SearchScreen: NemoRoutes(route = "search")
}
