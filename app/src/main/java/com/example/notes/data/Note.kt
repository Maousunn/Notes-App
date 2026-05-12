package com.example.notes.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Note entity for Room database.
 * Each note has a title, content, completion status, and timestamp.
 */
@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val content: String,
    var isCompleted: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)
