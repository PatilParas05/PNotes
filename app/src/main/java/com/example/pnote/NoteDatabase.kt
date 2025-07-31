package com.example.pnote

import androidx.room.Database
import android.content.Context
import androidx.room.Entity
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.Room
@Database(entities = [Note::class], version = 1,exportSchema = false)
@TypeConverters(DateConverters::class)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object{
    @Volatile
    private var INSTANCE: NoteDatabase? = null

    fun getDatabase(context: Context): NoteDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                NoteDatabase::class.java,
                "note_database"
            ).build()
            INSTANCE = instance
            instance
        }
    }


    }
}
