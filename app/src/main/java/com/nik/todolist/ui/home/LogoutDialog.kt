package com.nik.todolist.ui.home

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class LogoutDialog: DialogFragment() {
    interface LogoutListener {
        fun onLogout()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?) = AlertDialog.Builder(context)
        .setTitle("Выход")
        .setMessage("Вы уверены?")
        .setPositiveButton("Да") {_, _ -> (activity as LogoutListener).onLogout()}
        .setNegativeButton("Нет") {_, _ -> dismiss()}
        .create()
}