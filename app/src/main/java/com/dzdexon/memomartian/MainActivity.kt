package com.dzdexon.memomartian

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import com.dzdexon.memomartian.ui.theme.DarkColors
import com.dzdexon.memomartian.ui.theme.LightColors
import com.dzdexon.memomartian.ui.theme.LocalCustomColors
import com.dzdexon.memomartian.ui.theme.MemoMartianTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
//    var isDarkTheme by rememberSaveable { mutableStateOf(false) }
            var isDarkTheme = isSystemInDarkTheme()
            // Switch between dark and light colors
            CustomTheme(isDarkTheme = isDarkTheme) {
                MemoMartianTheme {
                    // A surface container using the 'background' color from the theme
                    NotesApp()
                }
            }
        }
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

