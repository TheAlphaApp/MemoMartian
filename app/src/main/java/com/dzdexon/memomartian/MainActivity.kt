package com.dzdexon.memomartian

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.dzdexon.memomartian.ui.theme.MemoMartianTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MemoMartianTheme {
                // A surface container using the 'background' color from the theme
                NotesApp()
            }
        }
    }
}