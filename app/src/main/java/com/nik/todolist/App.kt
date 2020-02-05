package com.nik.todolist

import android.app.Application
import com.github.ajalt.timberkt.Timber
import com.nik.todolist.di.appModule
import com.nik.todolist.di.homeModule
import com.nik.todolist.di.noteViewModel
import com.nik.todolist.di.splashModule
import org.koin.android.ext.android.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(timber.log.Timber.DebugTree())
        startKoin(this, listOf(appModule, splashModule, homeModule, noteViewModel))
    }
}