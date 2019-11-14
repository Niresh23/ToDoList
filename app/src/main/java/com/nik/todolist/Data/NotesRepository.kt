package com.nik.todolist.Data

import com.nik.todolist.Data.entity.Note
import com.nik.todolist.Data.provider.FireStoreProvider
import com.nik.todolist.Data.provider.RemoteDataProvider

object NotesRepository {
    private val remoteProvider: RemoteDataProvider = FireStoreProvider()

    fun getNotes() = remoteProvider.subscribeToAllNotes()
    fun saveNote(note: Note) = remoteProvider.saveNote(note)
    fun getNoteById(id: String) = remoteProvider.getNoteById(id)
    fun getCurrentUser() = remoteProvider.getCurrentUser()
}