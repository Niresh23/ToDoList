package com.nik.todolist.Data

import androidx.lifecycle.MutableLiveData
import com.nik.todolist.Data.intity.Note

object NotesRepository {

    private val notesLiveData = MutableLiveData<List<Note>>()


    private val notes = mutableListOf<Note>(
        Note("10:30", "To do something"))

    init{
        notesLiveData.value = notes
    }

    fun getNotes():List<Note> {
        return notes
    }

    fun saveNote(note: Note){
        addOrReplase(note)
        notesLiveData.value = notes
    }

    private fun addOrReplase(note: Note) {
        for(i in notes.indices) {
            if(notes[i] == note) {
                notes[i] = note
                return
            }
        }
        notes.add(note)
    }
}