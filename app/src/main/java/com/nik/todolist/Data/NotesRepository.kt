package com.nik.todolist.Data

import androidx.lifecycle.MutableLiveData
import com.nik.todolist.Data.intity.Note
import com.nik.todolist.Data.provider.FireStoreProvider
import com.nik.todolist.Data.provider.RemoteDataProvider

object NotesRepository {
    private val remoteProvider: RemoteDataProvider = FireStoreProvider()

    fun getNotes() = remoteProvider.subscribeToAllNotes()
    fun saveNote(note: Note) = remoteProvider.saveNote(note)
    fun getNoteById(id: String) = remoteProvider.getNoteById(id)
}