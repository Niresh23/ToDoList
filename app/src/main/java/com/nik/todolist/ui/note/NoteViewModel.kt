package com.nik.todolist.ui.note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nik.todolist.Data.NotesRepository
import com.nik.todolist.Data.intity.Note

class NoteViewModel : ViewModel() {
    private var pendingNote: Note? = null

    fun save(note: Note) {
        pendingNote = note
    }

    override fun onCleared() {
        pendingNote?.let {
            NotesRepository.saveNote(it)
        }
    }
}