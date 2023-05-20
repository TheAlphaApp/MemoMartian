package com.dzdexon.memomartian.ui.screens.managetags


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dzdexon.memomartian.AppViewModelProvider
import com.dzdexon.memomartian.model.Tag
import com.dzdexon.memomartian.navigation.NavigationDestination
import com.dzdexon.memomartian.ui.shared.component.CustomDialog
import com.dzdexon.memomartian.ui.shared.component.NoteTopAppBar
import kotlinx.coroutines.launch

object TagManageDestination : NavigationDestination {
    override val route: String = "tag_manage"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagManageScreen(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    viewModel: TagManageViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val tagState = viewModel.tagState.collectAsState()
    val tagList = tagState.value.tagList
    val coroutineScope = rememberCoroutineScope()
    var showTagAddDialog by remember {
        mutableStateOf(false)
    }
    var showTagUpdateDialog by remember {
        mutableStateOf(false)
    }
    var newTagString by remember {
        mutableStateOf("")
    }
    var updatingTag by remember {
        mutableStateOf<Tag?>(null)
    }
    var updateTagString by remember {
        mutableStateOf("")
    }
    Scaffold(
        modifier = modifier,
        topBar = {
            NoteTopAppBar(
                title = "Manage Tags",
                navigateUp = navigateUp,
                canNavigateBack = true,
                actions = {
                    Button(
                        onClick = {
                            newTagString = ""
                            showTagAddDialog = !showTagAddDialog }) {
                        Text("+ Add Tag")
                    }
                }
            )
        }
    ) { innerPadding ->

        LazyColumn(
            Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            items(items = tagList, key = { it.id }) { tag ->
                ListItem(

                    headlineContent = {
                        Text(text = tag.tagName)
                    },
                    leadingContent = {
                        Icon(imageVector = Icons.Default.Send, contentDescription = "Edit Item")

                    },
                    trailingContent = {
                        IconButton(onClick = {
                            updatingTag = tag
                            updateTagString = updatingTag?.tagName ?: ""
                            showTagUpdateDialog = true
                        }) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Item")
                        }
                    }


                )

            }
        }
        if (showTagAddDialog) {
            CustomDialog(
                onDismissRequest = {
                    showTagAddDialog = false
                },
                primaryButtonText = "Create Tag",
                primaryButtonEnabled = newTagString.isNotBlank() && newTagString.isNotEmpty(),
                onPrimaryButtonClick = {
                    coroutineScope.launch {
                        viewModel.createNewTag(newTagString)
                    }
                    showTagAddDialog = false
                },
                secondaryButtonText = "Cancel",
                onSecondaryButtonClick = {
                    newTagString = ""
                    showTagAddDialog = false
                }) {
                TextField(
                    value = newTagString,
                    onValueChange = {
                        newTagString = it
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    placeholder = { Text("Add Tag") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    )
                )
            }
        }

        if (showTagUpdateDialog && updatingTag != null) {
            CustomDialog(
                onDismissRequest = {
                    showTagUpdateDialog = false
                },
                primaryButtonText = "Update Tag",
                primaryButtonEnabled = updateTagString.isNotBlank() && updateTagString.isNotEmpty(),
                onPrimaryButtonClick = {
                    coroutineScope.launch {
                        viewModel.updateTag(updatingTag!!, updateTagString)
                    }
                    showTagUpdateDialog = false
                },
                secondaryButtonText = "Cancel",
                onSecondaryButtonClick = {
                    updateTagString = ""
                    showTagUpdateDialog = false
                }) {
                TextField(
                    value = updateTagString,
                    onValueChange = {
                        updateTagString = it
                    },
                    placeholder = {
                        Text(text = "Tag Name")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    )
                )
            }
        }

    }
}