package com.nik.todolist.Data.provider

import androidx.lifecycle.MutableLiveData
import com.github.ajalt.timberkt.Timber
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.nik.todolist.Data.entity.Note
import com.nik.todolist.Data.entity.User

import com.nik.todolist.Data.errors.NoAuthException

import com.nik.todolist.Data.model.NoteResult

class FireStoreProvider(private val firebaseAuth: FirebaseAuth,
                        private val store: FirebaseFirestore) : RemoteDataProvider {

    companion object {
        private const val NOTES_COLLECTION = "notes"
        private const val USERS_COLLECTION = "users"
    }


    private val currentUser
        get() = firebaseAuth.currentUser

    override fun getCurrentUser() = MutableLiveData<User?>().apply{
        value = currentUser?.let {
            User(it.displayName ?: "", it.email ?: "")
        }
    }

    override fun subscribeToAllNotes() = MutableLiveData<NoteResult>().apply {
        try {
            getUserNotesCollection().addSnapshotListener { snapshot, e ->
                e?.let { value = NoteResult.Error(it) }
                    ?: let {
                        snapshot?.let {
                            val notes = it.documents.map { it.toObject(Note::class.java) }
                            value = NoteResult.Success(notes)
                        }
                    }
            }
        } catch (e: Throwable) {
            value = NoteResult.Error(e)
        }
    }

    private fun getUserNotesCollection() = currentUser?.let {
        store.collection(USERS_COLLECTION).document(it.uid).collection(NOTES_COLLECTION)
    } ?: throw NoAuthException()

    override fun getNoteById(id: String) = MutableLiveData<NoteResult>().apply {
        try {
            getUserNotesCollection().document(id).get()
                .addOnSuccessListener { snapshot ->
                    value = NoteResult.Success(snapshot.toObject(Note::class.java))
                }.addOnFailureListener{ value = NoteResult.Error(it) }
        } catch (e:Throwable) {
            value = NoteResult.Error(e)
        }

    }

    override fun saveNote(note: Note) = MutableLiveData<NoteResult>().apply {
        try {
            getUserNotesCollection().document(note.id)
                .set(note)
                .addOnSuccessListener { _ ->
                    Timber.d { "Note $note is saved" }
                    value = NoteResult.Success(note)
                }.addOnFailureListener {
                    Timber.d { "Error saving note $note, message: ${it.message}"}
                    value = NoteResult.Error(it)
                }
        } catch (e:Throwable) {
            value = NoteResult.Error(e)
        }
    }
    override fun deleteNote(noteId: String) = MutableLiveData<NoteResult>().apply {
        try {
            getUserNotesCollection().document(noteId)
                .delete()
                .addOnSuccessListener { _ ->
                    value = NoteResult.Success(null)
                }.addOnFailureListener {
                    value = NoteResult.Error(it)
                }
        } catch (e:Throwable) {
            value = NoteResult.Error(e)
        }
    }
}