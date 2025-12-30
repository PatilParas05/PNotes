package com.example.pnote.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pnote.NoteViewModel
import com.example.pnote.ui.theme.PNoteTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(navController: NavController, noteViewModel: NoteViewModel) {
    val currentNote by noteViewModel.currentNote.collectAsState()

    var title by remember { mutableStateOf(currentNote?.title ?: "") }
    var content by remember { mutableStateOf(currentNote?.content ?: "") }

    val isTitleBlank = title.isBlank()
    val isContentBlank = content.isBlank()
    val isSaveEnabled = !isTitleBlank && !isContentBlank

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
                        enabled = true
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
                isError = showErrors && isTitleBlank,
                supportingText = {
                    if (showErrors && isTitleBlank) {
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
                isError = showErrors && isContentBlank,
                supportingText = {
                    if (showErrors && isContentBlank) {
                        Text("Content cannot be empty", color = MaterialTheme.colorScheme.error)
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun AddEditNoteScreenPreview_EmptyValidation() {
    PNoteTheme {
        val navController = rememberNavController()
        val dummyNoteViewModel = remember {
            object : NoteViewModel(object : com.example.pnote.NoteRepository(object : com.example.pnote.NoteDao {
                override fun getAllNotes(): kotlinx.coroutines.flow.Flow<List<com.example.pnote.Note>> = kotlinx.coroutines.flow.flowOf(emptyList())
                override suspend fun getNoteById(noteId: Int): com.example.pnote.Note? = null
                override suspend fun insert(note: com.example.pnote.Note) {}
                override suspend fun update(note: com.example.pnote.Note) {}
                override suspend fun delete(note: com.example.pnote.Note) {}
            }) {}) {
                override fun createNote(title: String, content: String) {}
                override fun updateNote(note: com.example.pnote.Note, newTitle: String, newContent: String) {}
                override fun selectNote(note: com.example.pnote.Note?) { _currentNote.value = note }
                init { _currentNote.value = null }
            }
        }
        AddEditNoteScreen(navController = navController, noteViewModel = dummyNoteViewModel)
    }
}