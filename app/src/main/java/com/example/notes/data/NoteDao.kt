package com.example.notes.data

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Data Access Object for Note entity.
 * Provides simple CRUD operations and filtered queries.
 */
@Dao
interface NoteDao {

    // Get all notes ordered by newest first
    @Query("SELECT * FROM notes ORDER BY timestamp DESC")
    fun getAllNotes(): LiveData<List<Note>>

    // Get only completed notes
    @Query("SELECT * FROM notes WHERE isCompleted = 1 ORDER BY timestamp DESC")
    fun getCompletedNotes(): LiveData<List<Note>>

    // Get only pending (not completed) notes
    @Query("SELECT * FROM notes WHERE isCompleted = 0 ORDER BY timestamp DESC")
    fun getPendingNotes(): LiveData<List<Note>>

    // Search notes by title or content
    @Query("SELECT * FROM notes WHERE title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%' ORDER BY timestamp DESC")
    fun searchNotes(query: String): LiveData<List<Note>>

    // Insert a new note
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    // Update an existing note
    @Update
    suspend fun updateNote(note: Note)

    // Delete a note
    @Delete
    suspend fun deleteNote(note: Note)

    // Delete a note by ID
    @Query("DELETE FROM notes WHERE id = :noteId")
    suspend fun deleteNoteById(noteId: Int)
}
