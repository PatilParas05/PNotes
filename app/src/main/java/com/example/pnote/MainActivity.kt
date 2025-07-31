package com.example.pnote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pnote.ui.AddEditNoteScreen
import com.example.pnote.ui.NoteListScreen
import com.example.pnote.ui.theme.PNoteTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database=NoteDatabase.getDatabase(this)
        val repository= NoteRepository(database.noteDao())
        enableEdgeToEdge()
        setContent {
            PNoteTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val noteViewModel : NoteViewModel =viewModel(factory = NoteViewModel.provideFactory(repository))
                    NavHost(navController=navController, startDestination = "noteList") {
                        composable("noteList") {
                            NoteListScreen(navController=navController, noteViewModel=noteViewModel)

                        }
                        composable("add_edit_note") {
                            AddEditNoteScreen(navController=navController, noteViewModel=noteViewModel)
                        }
                    }
                }
            }
        }
    }
}

fun Date.formatToString(): String{
    return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(this)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PNoteTheme {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Text(text = "PNote App Preview") // A placeholder, actual UI is in NoteListScreen
        }
    }
}