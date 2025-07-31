package com.example.pnote

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface NoteDao{
    @Query("SELECT * FROM notes ORDER BY ModifiedDate DESC")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE id = :noteId")
    suspend fun getNoteById(noteId: Int): Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notes: Note)
    @Delete
    suspend fun delete(notes: Note)
    @Update
    suspend fun update(notes: Note)


}