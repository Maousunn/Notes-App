package com.example.notes.viewmodel

import androidx.lifecycle.*
import com.example.notes.data.Note
import com.example.notes.data.NoteRepository
import kotlinx.coroutines.launch

/**
 * ViewModel that manages note data for the UI.
 * Uses LiveData so the UI automatically updates when data changes.
 *
 * SIMPLE LOGIC:
 * - allNotes, completedNotes, pendingNotes come directly from Room (auto-updating)
 * - currentFilter switches which LiveData the UI observes
 * - add/delete/toggle are one-line coroutine calls
 */
class NotesViewModel(private val repository: NoteRepository) : ViewModel() {

    // All three filtered lists come straight from Room
    val allNotes: LiveData<List<Note>> = repository.allNotes
    private val completedNotes: LiveData<List<Note>> = repository.completedNotes
    private val pendingNotes: LiveData<List<Note>> = repository.pendingNotes

    // Current filter type
    private val _currentFilter = MutableLiveData(FilterType.ALL)
    val currentFilter: LiveData<FilterType> = _currentFilter

    // The list the UI actually displays — switches based on the filter
    val displayedNotes: MediatorLiveData<List<Note>> = MediatorLiveData<List<Note>>().apply {
        addSource(allNotes) { if (_currentFilter.value == FilterType.ALL) value = it }
        addSource(completedNotes) { if (_currentFilter.value == FilterType.COMPLETED) value = it }
        addSource(pendingNotes) { if (_currentFilter.value == FilterType.PENDING) value = it }
        addSource(_currentFilter) { filter ->
            value = when (filter) {
                FilterType.ALL -> allNotes.value
                FilterType.COMPLETED -> completedNotes.value
                FilterType.PENDING -> pendingNotes.value
                else -> allNotes.value
            } ?: emptyList()
        }
    }

    // Search results
    fun searchNotes(query: String): LiveData<List<Note>> = repository.searchNotes(query)

    // ➕ Add a new note
    fun addNote(title: String, content: String) = viewModelScope.launch {
        val note = Note(title = title, content = content)
        repository.insert(note)
    }

    // ❌ Delete a note
    fun deleteNote(note: Note) = viewModelScope.launch {
        repository.delete(note)
    }

    // ✅ Toggle completed/pending
    fun toggleNote(note: Note) = viewModelScope.launch {
        val updated = note.copy(isCompleted = !note.isCompleted)
        repository.update(updated)
    }

    // 📝 Update a note
    fun updateNote(note: Note) = viewModelScope.launch {
        repository.update(note)
    }

    // 🔍 Set filter
    fun setFilter(type: FilterType) {
        _currentFilter.value = type
    }

    // Filter types
    enum class FilterType {
        ALL, COMPLETED, PENDING
    }
}

/**
 * Factory to create ViewModel with the repository dependency.
 * This is the standard way to pass parameters to a ViewModel.
 */
class NotesViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}