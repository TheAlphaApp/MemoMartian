package com.dzdexon.memomartian

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dzdexon.memomartian.navigation.NotesAppNavHost

@Composable
fun NotesApp(navController: NavHostController = rememberNavController()) {
    NotesAppNavHost(navController = navController)
}

