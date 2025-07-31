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
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavController
import com.example.pnote.Note
import com.example.pnote.NoteViewModel
import com.example.pnote.ui.theme.PNoteTheme
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(navController: NavController, noteViewModel: NoteViewModel) {
    val currentNote by noteViewModel.currentNote.collectAsState()

    var title by remember { mutableStateOf(currentNote?.title ?: "") }
    var content by remember { mutableStateOf(currentNote?.content ?: "") }

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
                    IconButton(onClick = {
                        if (currentNote == null) {
                            noteViewModel.createNote(title, content)
                        } else {
                            noteViewModel.updateNote(currentNote!!, title, content)
                        }
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.Check, contentDescription = "Save Note")
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
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Content") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Makes it fill remaining space
            )
        }
    }
}


@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun AddEditNoteScreenPreview_CreateNew() {
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
                // Override the methods that would be called in the UI to prevent crashes or unwanted behavior in preview
                override fun createNote(title: String, content: String) {
                    println("Preview: createNote called with title=$title, content=$content")
                }
                override fun updateNote(note: com.example.pnote.Note, newTitle: String, newContent: String) {
                    println("Preview: updateNote called for note ID ${note.id} with newTitle=$newTitle, newContent=$newContent")
                }
                override fun selectNote(note: com.example.pnote.Note?) {
                    _currentNote.value = note // Simulate selecting a note for preview
                }
                // Initialize _currentNote to null for "Create New Note" scenario
                init {
                    _currentNote.value = null
                }
            }
        }

        AddEditNoteScreen(navController = navController, noteViewModel = dummyNoteViewModel)
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun AddEditNoteScreenPreview_EditExisting() {
    PNoteTheme {
        val navController = rememberNavController()

        // Create a dummy NoteViewModel for the preview
        val dummyNoteViewModel = remember {
            object : NoteViewModel(object : com.example.pnote.NoteRepository(object : com.example.pnote.NoteDao {
                override fun getAllNotes(): kotlinx.coroutines.flow.Flow<List<com.example.pnote.Note>> = kotlinx.coroutines.flow.flowOf(emptyList())
                override suspend fun getNoteById(noteId: Int): com.example.pnote.Note? = null
                override suspend fun insert(note: com.example.pnote.Note) {}
                override suspend fun update(note: com.example.pnote.Note) {}
                override suspend fun delete(note: com.example.pnote.Note) {}
            }) {}) {
                override fun createNote(title: String, content: String) { /* do nothing */ }
                override fun updateNote(note: com.example.pnote.Note, newTitle: String, newContent: String) { /* do nothing */ }
                override fun selectNote(note: com.example.pnote.Note?) {
                    _currentNote.value = note
                }
                // Initialize _currentNote with a sample note for "Edit Note" scenario
                init {
                    _currentNote.value = Note(
                        id = 1,
                        title = "My Existing Note",
                        content = "This is some pre-filled content for editing.",
                        createdDate = Date(),
                        modifiedDate = Date()
                    )
                }
            }
        }

        AddEditNoteScreen(navController = navController, noteViewModel = dummyNoteViewModel)
    }
}