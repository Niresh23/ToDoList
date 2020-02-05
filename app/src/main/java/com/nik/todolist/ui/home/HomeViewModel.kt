package com.nik.todolist.ui.home

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.nik.todolist.Data.NotesRepository
import com.nik.todolist.Data.entity.Note
import com.nik.todolist.Data.model.NoteResult
import com.nik.todolist.ui.base.BaseViewModel

class HomeViewModel(private val notesRepository: NotesRepository) : BaseViewModel<List<Note>?, HomeViewState>() {

    private val noteObserver = Observer<NoteResult> {
        it ?: return@Observer

        when(it) {
            is NoteResult.Success<*> -> {
                viewStateLiveData.value = HomeViewState(notes = it.data as? List<Note>)
            }
            is NoteResult.Error -> {
                viewStateLiveData.value = HomeViewState(error = it.error)
            }
        }
    }

    private val repositoryNotes = notesRepository.getNotes()

    init {
        viewStateLiveData.value = HomeViewState()
        repositoryNotes.observeForever(noteObserver)
    }

    @VisibleForTesting
    public override fun onCleared() {
        repositoryNotes.removeObserver(noteObserver)
        super.onCleared()
    }
}