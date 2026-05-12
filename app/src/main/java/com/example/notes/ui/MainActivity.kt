package com.example.notes.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.NotesApplication
import com.example.notes.R
import com.example.notes.viewmodel.NotesViewModel
import com.example.notes.viewmodel.NotesViewModelFactory
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

/**
 * Main screen showing the list of notes with filter chips and a FAB to add new notes.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: NotesViewModel
    private lateinit var adapter: NotesAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var fabAddNote: FloatingActionButton
    private lateinit var chipAll: Chip
    private lateinit var chipCompleted: Chip
    private lateinit var chipPending: Chip
    private lateinit var tvEmptyState: TextView
    private lateinit var tvNoteCount: TextView
    private lateinit var emptyStateLayout: LinearLayout

    // Launcher for AddEditNoteActivity — refreshes the list when returning
    private val addNoteLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { /* Room LiveData auto-updates, no manual refresh needed */ }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize ViewModel with the repository from Application class
        val app = application as NotesApplication
        val factory = NotesViewModelFactory(app.repository)
        viewModel = ViewModelProvider(this, factory)[NotesViewModel::class.java]

        // Find views
        recyclerView = findViewById(R.id.rvNotes)
        fabAddNote = findViewById(R.id.fabAddNote)
        chipAll = findViewById(R.id.chipAll)
        chipCompleted = findViewById(R.id.chipCompleted)
        chipPending = findViewById(R.id.chipPending)
        tvEmptyState = findViewById(R.id.tvEmptyState)
        tvNoteCount = findViewById(R.id.tvNoteCount)
        emptyStateLayout = findViewById(R.id.emptyStateLayout)

        setupRecyclerView()
        setupFilterChips()
        setupFab()
        observeNotes()
    }

    private fun setupRecyclerView() {
        adapter = NotesAdapter(
            onNoteClick = { note ->
                // Open note for editing
                val intent = Intent(this, AddEditNoteActivity::class.java).apply {
                    putExtra("NOTE_ID", note.id)
                    putExtra("NOTE_TITLE", note.title)
                    putExtra("NOTE_CONTENT", note.content)
                }
                addNoteLauncher.launch(intent)
            },
            onNoteLongClick = { note ->
                // Show delete confirmation on long press
                showDeleteDialog(note)
            },
            onToggleComplete = { note ->
                viewModel.toggleNote(note)
            },
            onDeleteClick = { note ->
                showDeleteDialog(note)
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun setupFilterChips() {
        chipAll.setOnClickListener { viewModel.setFilter(NotesViewModel.FilterType.ALL) }
        chipCompleted.setOnClickListener { viewModel.setFilter(NotesViewModel.FilterType.COMPLETED) }
        chipPending.setOnClickListener { viewModel.setFilter(NotesViewModel.FilterType.PENDING) }

        // Observe filter changes to update chip selection state
        viewModel.currentFilter.observe(this) { filter ->
            chipAll.isChecked = filter == NotesViewModel.FilterType.ALL
            chipCompleted.isChecked = filter == NotesViewModel.FilterType.COMPLETED
            chipPending.isChecked = filter == NotesViewModel.FilterType.PENDING
        }
    }

    private fun setupFab() {
        fabAddNote.setOnClickListener {
            val intent = Intent(this, AddEditNoteActivity::class.java)
            addNoteLauncher.launch(intent)
        }
    }

    private fun observeNotes() {
        viewModel.displayedNotes.observe(this) { notes ->
            adapter.submitList(notes)

            // Show/hide empty state
            if (notes.isNullOrEmpty()) {
                emptyStateLayout.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            } else {
                emptyStateLayout.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
            }

            // Update note count
            val count = notes?.size ?: 0
            tvNoteCount.text = "$count ${if (count == 1) "note" else "notes"}"
        }
    }

    private fun showDeleteDialog(note: com.example.notes.data.Note) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Delete Note")
            .setMessage("Are you sure you want to delete \"${note.title}\"?")
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Delete") { _, _ ->
                viewModel.deleteNote(note)
                Snackbar.make(recyclerView, "Note deleted", Snackbar.LENGTH_SHORT).show()
            }
            .show()
    }
}