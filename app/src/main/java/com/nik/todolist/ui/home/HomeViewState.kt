package com.nik.todolist.ui.home

import com.nik.todolist.Data.entity.Note
import com.nik.todolist.ui.base.BaseViewState

class HomeViewState(val notes: List<Note>? = null, error: Throwable? = null) : BaseViewState<List<Note>?>(notes, error)