package com.dzdexon.memomartian

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dzdexon.memomartian.navigation.NotesAppNavHost
import com.dzdexon.memomartian.ui.theme.DarkColors
import com.dzdexon.memomartian.ui.theme.LightColors
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.runtime.setValue
import com.dzdexon.memomartian.ui.theme.LocalCustomColors

@Composable
fun NotesApp(navController: NavHostController = rememberNavController()) {

        NotesAppNavHost(navController = navController)

}

