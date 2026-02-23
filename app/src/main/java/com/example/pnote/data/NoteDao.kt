package com.example.pnote.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao{
    @Query("SELECT * FROM notes ORDER BY ModifiedDate DESC")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE id = :noteId")
    suspend fun getNoteById(noteId: Int): Note?

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(notes: Note)
    @Delete
    suspend fun delete(notes: Note)
    @Update
    suspend fun update(notes: Note)


}