package com.example.pnote.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.pnote.data.Note
import com.example.pnote.formatToString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(navController: NavController, noteViewModel: NoteViewModel) {
    val notes by noteViewModel.allNotes.collectAsStateWithLifecycle(initialValue = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("My PNotes") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                noteViewModel.selectNote(null)
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
                            noteViewModel.selectNote(note)
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
                text = "Modified: ${note.modifiedDate.formatToString()}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { onDeleteClick(note) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Note")
                }
            }
        }
    }
}
