package com.nik.todolist.Data.provider

import androidx.lifecycle.LiveData
import com.nik.todolist.Data.intity.Note
import com.nik.todolist.Data.model.NoteResult

interface RemoteDataProvider {
    fun subscribeToAllNotes() : LiveData<NoteResult>
    fun getNoteById(id: String) : LiveData<NoteResult>
    fun saveNote(note : Note) : LiveData<NoteResult>
}