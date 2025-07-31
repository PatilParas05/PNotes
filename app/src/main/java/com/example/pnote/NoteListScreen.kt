package com.example.pnote.ui // This package is correct for your UI screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pnote.Note // Correct as per your current project structure (Note is in com.example.pnote)
import com.example.pnote.NoteViewModel // Correct as per your current project structure (NoteViewModel is in com.example.pnote)
import com.example.pnote.formatToString // This import MUST be here if formatToString is in com.example.pnote

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(navController: NavController, noteViewModel: NoteViewModel) {
    val notes by noteViewModel.allNotes.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("My PNotes") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                // This calls the 'selectNote' function on your NoteViewModel instance
                noteViewModel.selectNote(null) // Corrected method name
                navController.navigate("add_edit_note")
            }) {
                Icon(Icons.Filled.Add, "Add new note")
            }
        }
    ) { paddingValues ->
        if (notes.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text("No notes yet. Click + to add one!")
            }
        } else {
            LazyColumn(modifier = Modifier.padding(paddingValues)) {
                items(notes) { note ->
                    NoteItem(
                        note = note,
                        onNoteClick = {
                            // This calls the 'selectNote' function on your NoteViewModel instance
                            noteViewModel.selectNote(note) // Corrected method name
                            navController.navigate("add_edit_note")
                        },
                        onDeleteClick = {
                            noteViewModel.deleteNote(note)
                        }
                    )
                    Divider()
                }
            }
        }
    }
}

@Composable
fun NoteItem(note: Note, onNoteClick: (Note) -> Unit, onDeleteClick: (Note) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onNoteClick(note) }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = note.title, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = note.content, style = MaterialTheme.typography.bodyMedium, maxLines = 2)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                // Calls the 'formatToString' extension function on the 'modifiedDate' property of the Note object
                text = "Modified: ${note.modifiedDate.formatToString()}", // Corrected property name and function call
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                // Delete button in NoteItem for quick deletion
                IconButton(onClick = { onDeleteClick(note) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Note")
                }
            }
        }
    }
}