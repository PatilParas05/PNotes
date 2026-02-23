package com.example.pnote.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(navController: NavController, noteViewModel: NoteViewModel) {
    val currentNote by noteViewModel.currentNote.collectAsStateWithLifecycle()

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    LaunchedEffect(currentNote) {
        title = currentNote?.title ?: ""
        content = currentNote?.content ?: ""
    }

    val isSaveEnabled by remember { derivedStateOf { title.isNotBlank() && content.isNotBlank() } }

    var showErrors by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (currentNote == null) "Create New Note" else "Edit Note") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            if (isSaveEnabled) {
                                if (currentNote == null) {
                                    noteViewModel.createNote(title, content)
                                } else {
                                    noteViewModel.updateNote(currentNote!!, title, content)
                                }
                                navController.popBackStack()
                            } else {
                                showErrors = true
                            }
                        },
                        enabled = isSaveEnabled
                    ) {
                        Icon(
                            Icons.Filled.Check,
                            contentDescription = "Save Note",
                            tint = if (isSaveEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                    if (it.isNotBlank()) showErrors = false
                },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth(),
                isError = showErrors && title.isBlank(),
                supportingText = {
                    if (showErrors && title.isBlank()) {
                        Text("Title cannot be empty", color = MaterialTheme.colorScheme.error)
                    }
                },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = content,
                onValueChange = {
                    content = it
                    if (it.isNotBlank()) showErrors = false
                },
                label = { Text("Content") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                isError = showErrors && content.isBlank(),
                supportingText = {
                    if (showErrors && content.isBlank()) {
                        Text("Content cannot be empty", color = MaterialTheme.colorScheme.error)
                    }
                }
            )
        }
    }
}
