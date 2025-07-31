package com.example.pnote

import kotlinx.coroutines.flow.Flow
import java.util.Date

open class NoteRepository (private val noteDao: NoteDao){
    val allNotes: Flow<List<Note>> = noteDao.getAllNotes()

    suspend fun insert(notes: Note){
        noteDao.insert(notes)
        }
    suspend fun delete(notes: Note){
        noteDao.delete(notes)
    }
    suspend fun update(notes: Note){
        noteDao.update(notes.copy(modifiedDate = Date()))
    }
    suspend fun getNoteById(id: Int): Note?{
        return noteDao.getNoteById(id)

    }
}