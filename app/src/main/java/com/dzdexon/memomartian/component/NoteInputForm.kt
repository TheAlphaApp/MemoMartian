package com.dzdexon.memomartian.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dzdexon.memomartian.screens.create.NoteUiState
import com.dzdexon.memomartian.ui.theme.MemoMartianTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteInputForm(
    noteUiState: NoteUiState,
    modifier: Modifier = Modifier,
    onValueChange: (NoteUiState) -> Unit = {},
    enabled: Boolean = true
) {
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {

        TextField(
            value = noteUiState.title,
            onValueChange = { onValueChange(noteUiState.copy(title = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                containerColor = Color.Transparent
            )
        )
//        Divider()
        TextField(
            value = noteUiState.content,
            onValueChange = { onValueChange(noteUiState.copy(content = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            label = { Text("Content") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = false,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                containerColor = Color.Transparent

            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InputFormPreview() {
    MemoMartianTheme {
        NoteInputForm(noteUiState = NoteUiState())
    }
}