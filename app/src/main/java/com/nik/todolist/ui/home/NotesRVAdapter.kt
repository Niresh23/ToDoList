package com.nik.todolist.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nik.todolist.Data.intity.Note
import com.nik.todolist.R
import kotlinx.android.synthetic.main.item_list_layout.view.*

class NotesRVAdapter(val onItemClick: ((Note) -> Unit)? = null) : RecyclerView.Adapter<NotesRVAdapter.ViewHolder>() {

    var notes: List<Note> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int)  = holder.bind(notes[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context)
        .inflate(R.layout.item_list_layout, parent, false))

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(note: Note) = with(itemView) {
            tv_time.text = note.time
            tv_doing.text = note.text

            itemView.setOnClickListener{
                onItemClick?.invoke(note)
            }
        }
    }
}