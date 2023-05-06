package com.dzdexon.memomartian.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp
import com.dzdexon.memomartian.screens.create.NoteUiState


@Composable
fun EditNoteBody(
    noteUiState: NoteUiState,
    onNoteValueChange: (NoteUiState) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            NoteInputForm(noteUiState = noteUiState, onValueChange = onNoteValueChange)
            if (noteUiState.isValid) Button(
                onClick = onSaveClick,
                enabled = noteUiState.isValid,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Note")
            }
        }
    }

}
