package com.nik.todolist.ui.note

import com.nik.todolist.Data.NotesRepository
import com.nik.todolist.Data.entity.Note
import com.nik.todolist.Data.model.NoteResult
import com.nik.todolist.ui.base.BaseViewModel

class NoteViewModel(private val notesRepository: NotesRepository) : BaseViewModel<NoteViewState.Data, NoteViewState>() {

//    init {
//        viewStateLiveData.value = NoteViewState()
//    }

    private val pendingNote: Note?
        get() = viewStateLiveData.value?.data?.note

    fun save(note: Note) {
        viewStateLiveData.value = NoteViewState(NoteViewState.Data(note = note))
    }

    override fun onCleared() {
        pendingNote?.let {
            notesRepository.saveNote(it)
        }
    }

    fun loadNote(noteId: String) {
        notesRepository.getNoteById(noteId).observeForever {
            it ?: return@observeForever
            when (it) {
                is NoteResult.Success<*> -> viewStateLiveData.value = NoteViewState(NoteViewState.Data(note = it.data as? Note))
                is NoteResult.Error -> viewStateLiveData.value = NoteViewState(error = it.error)
            }
        }
    }

    fun deleteNote() {
        pendingNote?.let {
            notesRepository.deleteNote(it.id).observeForever { result ->
                result?.let {result ->
                    when (result) {
                        is NoteResult.Success<*> -> viewStateLiveData.value = NoteViewState(NoteViewState.Data(isDeleted = true))
                        is NoteResult.Error -> viewStateLiveData.value = NoteViewState(error = result.error)
                    }
                }
            }
        }
    }
}