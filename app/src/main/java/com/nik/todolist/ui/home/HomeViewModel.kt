package com.nik.todolist.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nik.todolist.Data.NotesRepository

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }

    val text: LiveData<String> = _text

    private val viewStateLiveData: MutableLiveData<HomeViewState> = MutableLiveData()

    init {
        viewStateLiveData.value = HomeViewState(NotesRepository.getNotes())
    }
    fun viewState(): LiveData<HomeViewState> = viewStateLiveData


}