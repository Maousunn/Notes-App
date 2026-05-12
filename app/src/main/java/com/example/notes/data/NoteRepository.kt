package com.example.notes.data

import androidx.lifecycle.LiveData

/**
 * Repository acts as a clean API between ViewModel and the database.
 * This keeps the ViewModel simple — it just calls repository methods.
 */
class NoteRepository(private val noteDao: NoteDao) {

    // All notes from the database (LiveData auto-updates the UI)
    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    // Filtered lists
    val completedNotes: LiveData<List<Note>> = noteDao.getCompletedNotes()
    val pendingNotes: LiveData<List<Note>> = noteDao.getPendingNotes()

    // Search notes
    fun searchNotes(query: String): LiveData<List<Note>> = noteDao.searchNotes(query)

    // Insert
    suspend fun insert(note: Note) = noteDao.insertNote(note)

    // Update
    suspend fun update(note: Note) = noteDao.updateNote(note)

    // Delete
    suspend fun delete(note: Note) = noteDao.deleteNote(note)

    // Delete by ID
    suspend fun deleteById(noteId: Int) = noteDao.deleteNoteById(noteId)
}
