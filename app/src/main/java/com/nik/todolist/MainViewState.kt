package com.nik.todolist

import com.nik.todolist.Data.intity.Note
import com.nik.todolist.ui.base.BaseViewState

class MainViewState(val notes: List<Note>? = null, error: Throwable? = null)
    : BaseViewState<List<Note>?>(notes, error)