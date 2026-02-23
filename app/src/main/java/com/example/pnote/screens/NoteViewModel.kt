package com.example.pnote.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.pnote.data.Note
import com.example.pnote.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date

open class NoteViewModel(private val repository: NoteRepository): ViewModel(){
    val allNotes: StateFlow<List<Note>> = repository.allNotes.stateIn(viewModelScope, SharingStarted.Companion.Lazily, emptyList())
    private val _currentNote = MutableStateFlow<Note?>(null)
    val currentNote: StateFlow<Note?> = _currentNote.asStateFlow()
    open fun createNote(title: String, content: String){
        viewModelScope.launch {
            val newNote = Note(
                title = title,
                content = content,
                createdDate = Date(),
                modifiedDate = Date()
            )
            repository.insert(newNote)
        }
    }

    open fun updateNote(note: Note, newTitle: String, newContent: String){
        viewModelScope.launch {
            repository.update(note.copy(title = newTitle, content = newContent, modifiedDate = Date()))
        }
    }
    fun deleteNote(note: Note){
        viewModelScope.launch {
            repository.delete(note)
        }
    }
    open fun selectNote(note: Note?) {
        _currentNote.value = note
    }
    companion object{
        fun provideFactory(repository: NoteRepository): ViewModelProvider.Factory =object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return NoteViewModel(repository) as T
            }
        }

    }
}