package com.nik.todolist

import androidx.lifecycle.Observer
import com.nik.todolist.Data.NotesRepository
import com.nik.todolist.Data.intity.Note
import com.nik.todolist.Data.model.NoteResult
import com.nik.todolist.ui.base.BaseViewModel

class MainViewModel : BaseViewModel<List<Note>?, MainViewState>(){
    private val noteObserver = Observer<NoteResult> {
        it ?: return@Observer

        when(it) {
            is NoteResult.Success<*> -> {
                viewStateLiveData.value = MainViewState(notes = it.data as? List<Note>)
            }
            is NoteResult.Error -> {
                viewStateLiveData.value = MainViewState(error = it.error)
            }
        }
    }

    private val repositoryNotes = NotesRepository.getNotes()

    init{
        viewStateLiveData.value = MainViewState()
        repositoryNotes.observeForever(noteObserver)
    }

    override fun onCleared() {
        repositoryNotes.removeObserver(noteObserver)
        super.onCleared()
    }
}