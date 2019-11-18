package com.nik.todolist.ui.note

import com.nik.todolist.Data.entity.Note
import com.nik.todolist.ui.base.BaseViewState

class NoteViewState(data: Data = Data(), error: Throwable? = null) :
    BaseViewState<NoteViewState.Data>(data, error) {
    data class Data(val isDeleted: Boolean = false, val note: Note? = null)
}