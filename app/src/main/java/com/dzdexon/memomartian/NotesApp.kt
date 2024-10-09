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
//    var isDarkTheme by rememberSaveable { mutableStateOf(false) }
    var isDarkTheme = isSystemInDarkTheme()
    // Switch between dark and light colors
    CustomTheme(isDarkTheme = isDarkTheme) {
        NotesAppNavHost(navController = navController)
    }
}



@Composable
fun CustomTheme(
    isDarkTheme: Boolean = false, // Flag to toggle between themes
    content: @Composable () -> Unit
) {
    // Choose the appropriate colors based on the theme
    val colors = remember { if (isDarkTheme) DarkColors else LightColors }

    // Provide the CustomColors to the composable hierarchy
    CompositionLocalProvider(LocalCustomColors provides colors) {
        content() // Wrap content within the color provider
    }
}

