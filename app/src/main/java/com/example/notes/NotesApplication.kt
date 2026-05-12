package com.example.notes

import android.app.Application
import com.example.notes.data.NoteDatabase
import com.example.notes.data.NoteRepository

/**
 * Application class to initialize the database and repository once.
 * This avoids creating multiple database instances.
 */
class NotesApplication : Application() {

    // Lazy initialization — database is created only when first accessed
    val database by lazy { NoteDatabase.getDatabase(this) }
    val repository by lazy { NoteRepository(database.noteDao()) }
}
