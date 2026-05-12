package com.example.notes.ui

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.notes.NotesApplication
import com.example.notes.R
import com.example.notes.data.Note
import com.example.notes.viewmodel.NotesViewModel
import com.example.notes.viewmodel.NotesViewModelFactory
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Activity for adding a new note or editing an existing one.
 * Simple form with title and content fields.
 */
class AddEditNoteActivity : AppCompatActivity() {

    private lateinit var viewModel: NotesViewModel
    private lateinit var edtTitle: EditText
    private lateinit var edtContent: EditText
    private lateinit var fabSave: FloatingActionButton
    private lateinit var toolbar: MaterialToolbar

    // If editing, these hold the existing note's data
    private var noteId: Int = -1
    private var isEditing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)

        // Initialize ViewModel
        val app = application as NotesApplication
        val factory = NotesViewModelFactory(app.repository)
        viewModel = ViewModelProvider(this, factory)[NotesViewModel::class.java]

        // Find views
        edtTitle = findViewById(R.id.edtNoteTitle)
        edtContent = findViewById(R.id.edtNoteContent)
        fabSave = findViewById(R.id.fabSaveNote)
        toolbar = findViewById(R.id.toolbar)

        // Setup toolbar with back navigation
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        // Check if we're editing an existing note
        noteId = intent.getIntExtra("NOTE_ID", -1)
        if (noteId != -1) {
            isEditing = true
            edtTitle.setText(intent.getStringExtra("NOTE_TITLE") ?: "")
            edtContent.setText(intent.getStringExtra("NOTE_CONTENT") ?: "")
            supportActionBar?.title = "Edit Note"
        } else {
            supportActionBar?.title = "New Note"
        }

        // Save button
        fabSave.setOnClickListener { saveNote() }
    }

    private fun saveNote() {
        val title = edtTitle.text.toString().trim()
        val content = edtContent.text.toString().trim()

        if (title.isBlank()) {
            edtTitle.error = "Title cannot be empty"
            return
        }

        if (isEditing) {
            // Update existing note
            val updatedNote = Note(
                id = noteId,
                title = title,
                content = content,
                isCompleted = false,
                timestamp = System.currentTimeMillis()
            )
            viewModel.updateNote(updatedNote)
            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show()
        } else {
            // Add new note
            viewModel.addNote(title, content)
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show()
        }

        finish()
    }
}
