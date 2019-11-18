package com.nik.todolist.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.nik.todolist.Data.NotesRepository
import com.nik.todolist.Data.provider.FireStoreProvider
import com.nik.todolist.Data.provider.RemoteDataProvider
import com.nik.todolist.ui.home.HomeViewModel
import com.nik.todolist.ui.note.NoteViewModel
import com.nik.todolist.ui.spalsh.SplashViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module


val appModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single<RemoteDataProvider> { FireStoreProvider(get(), get()) }
    single { NotesRepository(get())}
}

val splashModule = module {
    viewModel { SplashViewModel(get()) }
}

val homeModule = module {
    viewModel { HomeViewModel(get()) }
}

val noteViewModel = module {
    viewModel { NoteViewModel(get()) }
}