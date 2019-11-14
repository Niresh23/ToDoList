package com.nik.todolist.Data.provider

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.ajalt.timberkt.Timber
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.nik.todolist.Data.entity.Note
import com.nik.todolist.Data.entity.User
import com.nik.todolist.Data.model.NoteResult

class FireStoreProvider : RemoteDataProvider {

    companion object {
        private const val NOTES_COLLECTION = "notes"
    }

    private val store by lazy { FirebaseFirestore.getInstance() }
    private val notesReference by lazy { store.collection(NOTES_COLLECTION) }

    private val currentUser
        get() = FirebaseAuth.getInstance().currentUser

    override fun getCurrentUser() = MutableLiveData<User?>().apply{
        value = currentUser?.let {
            User(it.displayName ?: "", it.email ?: "")
        }
    }
    override fun subscribeToAllNotes() = MutableLiveData<NoteResult>().apply{
        notesReference.addSnapshotListener{snapshot, e ->
            e?.let{ value = NoteResult.Error(it) }
                ?: let{
                    snapshot?.let {
                        val notes = mutableListOf<Note>()
                        for (doc: QueryDocumentSnapshot in snapshot) {
                            notes.add(doc.toObject(Note::class.java))
                        }
                        value = NoteResult.Success(notes)
                    }
                }
        }
    }

    override fun getNoteById(id: String) = MutableLiveData<NoteResult>().apply {
        notesReference.document(id).get()
            .addOnSuccessListener { snapshot ->
                value = NoteResult.Success(snapshot.toObject(Note::class.java))
            }.addOnFailureListener{ value = NoteResult.Error(it) }
    }

    override fun saveNote(note: Note) = MutableLiveData<NoteResult>().apply {
        notesReference.document(note.id)
            .set(note)
            .addOnSuccessListener { snapshot ->
                Timber.d { "Note $note is saved" }
                value = NoteResult.Success(note)
            }.addOnFailureListener {
                Timber.d { "Error saving note $note, message: ${it.message}"}
                value = NoteResult.Error(it)
            }
    }
}