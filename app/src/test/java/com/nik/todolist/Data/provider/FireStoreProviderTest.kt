package com.nik.todolist.Data.provider


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import com.nik.todolist.Data.entity.Note
import com.nik.todolist.Data.errors.NoAuthException
import com.nik.todolist.Data.model.NoteResult
import io.mockk.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

import org.junit.Before
import org.junit.Rule
import org.junit.Test


class FireStoreProviderTest {
    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()


    private val mockkDb = mockk<FirebaseFirestore>()
    private val mockkAuth = mockk<FirebaseAuth>()
    private val mockkResultCollection = mockk<CollectionReference>()
    private val mockkUser = mockk<FirebaseUser>()

    private val mockkDocumentSnapshot1 = mockk<DocumentSnapshot>()
    private val mockkDocumentSnapshot2 = mockk<DocumentSnapshot>()
    private val mockkDocumentSnapshot3 = mockk<DocumentSnapshot>()

    private val testNotes = listOf(
        Note("1"),
        Note("2"),
        Note("3")
    )

    private val provider = FireStoreProvider(mockkAuth, mockkDb)
    @Before
    fun setUp() {
        clearMocks(mockkUser, mockkResultCollection, mockkDocumentSnapshot1,
            mockkDocumentSnapshot2, mockkDocumentSnapshot3)

        every { mockkAuth.currentUser } returns mockkUser
        every { mockkUser.uid } returns ""
        every { mockkDb
            .collection(any())
            .document(any())
            .collection(any()) } returns mockkResultCollection
        every { mockkDocumentSnapshot1.toObject(Note::class.java) } returns testNotes[0]
        every { mockkDocumentSnapshot2.toObject(Note::class.java) } returns testNotes[1]
        every { mockkDocumentSnapshot3.toObject(Note::class.java) } returns testNotes[2]

    }

    @Test
    fun `should throw NoAuthException`() {
        var result : Any? = null
        every { mockkAuth.currentUser } returns null
        provider.subscribeToAllNotes().observeForever {
            result = (it as? NoteResult.Error)?.error
        }
        assertTrue(result is NoAuthException)
    }
    @Test
    fun `subscribeToAllNotes returns notes`() {
        var result: List<Note>? = null
        val mockkSnapshot = mockk<QuerySnapshot>()
        val slot = slot<EventListener<QuerySnapshot>>()
        every { mockkSnapshot.documents } returns listOf(mockkDocumentSnapshot1,
                                                    mockkDocumentSnapshot2, mockkDocumentSnapshot3)
        every { mockkResultCollection.addSnapshotListener(capture(slot)) } returns mockk()

        provider.subscribeToAllNotes().observeForever {
            result = (it as? NoteResult.Success<List<Note>>)?.data
        }
        slot.captured.onEvent(mockkSnapshot, null)
        assertEquals(testNotes, result)
    }

    @Test
    fun `subscribeToAllNotes returns error`() {
        var result: Throwable? = null
        val testError = mockk<FirebaseFirestoreException>()
        val slot = slot<EventListener<QuerySnapshot>>()
        every { mockkResultCollection.addSnapshotListener(capture(slot)) } returns mockk()

        provider.subscribeToAllNotes().observeForever {
            result = (it as? NoteResult.Error)?.error
        }
        slot.captured.onEvent(null, testError)
        assertEquals(testError, result)
    }

    @Test
    fun `saveNote calls set`() {
        val mockkDocumentReference = mockk<DocumentReference>()
        every { mockkResultCollection.document(testNotes[0].id) } returns mockkDocumentReference
        provider.saveNote(testNotes[0])
        verify(exactly = 1) {mockkDocumentReference.set(testNotes[0])}
    }

    @Test
    fun `saveNote returns note`() {
        var result: Note? = null
        val mockkDocumentReference = mockk<DocumentReference>()
        val slot = slot<OnSuccessListener<in Void>>()

        every { mockkResultCollection.document(testNotes[0].id) } returns mockkDocumentReference

        every { mockkDocumentReference.set(testNotes[0])
                .addOnSuccessListener(capture(slot))}returns mockk()

        provider.saveNote(testNotes[0]).observeForever {
            result = (it as? NoteResult.Success<Note>)?.data
        }

        slot.captured.onSuccess(null)
        assertEquals(testNotes[0], result)
    }



}