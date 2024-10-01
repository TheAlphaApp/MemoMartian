package com.dzdexon.memomartian.ui.shared.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dzdexon.memomartian.model.Note
import com.dzdexon.memomartian.model.Tag
import com.dzdexon.memomartian.ui.screens.edit.EditScreenViewModel

@Composable
fun EditNoteBody(
    note: Note,
    selectedTags: List<Tag>,
    viewModelEdit: EditScreenViewModel,
    allTags: List<Tag>,
    onSaveClick: () -> Unit,
    addTagToNote: (Tag) -> Unit,
    removeTagFromNote: (Tag) -> Unit,
    createNewTag: (String) -> Unit,
    modifier: Modifier = Modifier,
) {

    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            NoteInputForm(
                viewModelEdit = viewModelEdit,
                note = note,
                tagList = allTags,
                addTagToNote = addTagToNote,
                removeTagFromNote = removeTagFromNote,
                onSaveClick = onSaveClick,
                selectedTags = selectedTags,
                createNewTag = createNewTag,
            )

        }
    }

}
