package com.example.notes.ui

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.data.Note
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * RecyclerView adapter using ListAdapter + DiffUtil for efficient updates.
 * Handles click, long-click, and checkbox toggle events.
 */
class NotesAdapter(
    private val onNoteClick: (Note) -> Unit,
    private val onNoteLongClick: (Note) -> Unit,
    private val onToggleComplete: (Note) -> Unit,
    private val onDeleteClick: (Note) -> Unit
) : ListAdapter<Note, NotesAdapter.NoteViewHolder>(NoteDiffCallback()) {

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvNoteTitle)
        val tvContent: TextView = itemView.findViewById(R.id.tvNoteContent)
        val tvTimestamp: TextView = itemView.findViewById(R.id.tvTimestamp)
        val cbCompleted: CheckBox = itemView.findViewById(R.id.cbCompleted)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = getItem(position)

        holder.tvTitle.text = note.title
        holder.tvContent.text = note.content
        holder.tvTimestamp.text = formatTimestamp(note.timestamp)

        // Checkbox state (without triggering listener)
        holder.cbCompleted.setOnCheckedChangeListener(null)
        holder.cbCompleted.isChecked = note.isCompleted

        // Strikethrough effect for completed notes
        if (note.isCompleted) {
            holder.tvTitle.paintFlags = holder.tvTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.tvTitle.alpha = 0.5f
            holder.tvContent.alpha = 0.4f
        } else {
            holder.tvTitle.paintFlags = holder.tvTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            holder.tvTitle.alpha = 1.0f
            holder.tvContent.alpha = 0.7f
        }

        // Click listeners
        holder.itemView.setOnClickListener { onNoteClick(note) }
        holder.itemView.setOnLongClickListener {
            onNoteLongClick(note)
            true
        }
        holder.cbCompleted.setOnCheckedChangeListener { _, _ ->
            onToggleComplete(note)
        }
        holder.btnDelete.setOnClickListener { onDeleteClick(note) }
    }

    // Format timestamp to readable date
    private fun formatTimestamp(timestamp: Long): String {
        val sdf = SimpleDateFormat("MMM dd, yyyy • hh:mm a", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }
}

/**
 * DiffUtil callback for efficient list updates.
 * Only re-renders items that actually changed.
 */
class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }
}