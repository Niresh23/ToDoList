package com.nik.todolist.Data

import com.nik.todolist.Data.entity.Note
import com.nik.todolist.Data.provider.RemoteDataProvider

class NotesRepository(private val remoteProvider: RemoteDataProvider) {

    fun getNotes() = remoteProvider.subscribeToAllNotes()
    fun saveNote(note: Note) = remoteProvider.saveNote(note)
    fun getNoteById(id: String) = remoteProvider.getNoteById(id)
    fun getCurrentUser() = remoteProvider.getCurrentUser()
    fun deleteNote(noteId: String) = remoteProvider.deleteNote(noteId)
}