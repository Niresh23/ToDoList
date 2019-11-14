package com.nik.todolist.ui.spalsh

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nik.todolist.Data.NotesRepository
import com.nik.todolist.Data.errors.NoAuthException
import com.nik.todolist.ui.base.BaseViewModel
import com.nik.todolist.ui.base.BaseViewState

class SplashViewModel : BaseViewModel<Boolean?, SplashViewState>() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is send Fragment"
    }
    val text: LiveData<String> = _text
    fun requestUser() {
        NotesRepository.getCurrentUser().observeForever {
            viewStateLiveData.value = if(it != null) {
                SplashViewState(anthenticated = true)
            } else {
                SplashViewState(error = NoAuthException())
            }
        }
    }
}