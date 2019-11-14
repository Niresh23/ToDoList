package com.nik.todolist.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.nik.todolist.Data.NotesRepository
import com.nik.todolist.Data.entity.Note
import com.nik.todolist.Data.model.NoteResult
import com.nik.todolist.ui.base.BaseViewModel

class HomeViewModel : BaseViewModel<List<Note>?, HomeViewState>() {

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

    private val repositoryNotes = NotesRepository.getNotes()

    init {
        viewStateLiveData.value = HomeViewState()
        repositoryNotes.observeForever(noteObserver)
    }

    override fun onCleared() {
        repositoryNotes.removeObserver(noteObserver)
        super.onCleared()
    }
    fun viewState(): LiveData<HomeViewState> = viewStateLiveData
}