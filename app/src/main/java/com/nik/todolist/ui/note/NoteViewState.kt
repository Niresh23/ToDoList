package com.nik.todolist.ui.note

import com.nik.todolist.Data.intity.Note
import com.nik.todolist.ui.base.BaseViewState

class NoteViewState(note: Note? = null, error: Throwable? = null) : BaseViewState<Note?>(note, error)