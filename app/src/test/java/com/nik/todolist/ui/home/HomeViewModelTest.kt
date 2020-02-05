package com.nik.todolist.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.nik.todolist.Data.NotesRepository
import com.nik.todolist.Data.entity.Note
import com.nik.todolist.Data.model.NoteResult
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {
    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val mockkRepository = mockk<NotesRepository>()
    private val notesLiveData = MutableLiveData<NoteResult>()
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        clearAllMocks()
        every { mockkRepository.getNotes() } returns notesLiveData
        viewModel = HomeViewModel(mockkRepository)
    }

    @Test
    fun `should call getNotes`() {
        verify(exactly = 1) { mockkRepository.getNotes() }
    }

    @Test
    fun `should returns Notes`() {
        var result:List<Note>? = null
        val testData = listOf(Note("1"), Note("2"))
        viewModel.getViewState().observeForever {
            result = it?.data
        }
        notesLiveData.value = NoteResult.Success(testData)
        assertEquals(testData, result)
    }

    @Test
    fun `should returns Error`() {
        var result:Throwable? = null
        val testData = Throwable("error")
        viewModel.getViewState().observeForever {
            result = it?.error
        }
        notesLiveData.value = NoteResult.Error(error = testData)
        assertEquals(testData, result)
    }

    @Test
    fun `should remove observer`() {
        viewModel.onCleared()
        assertFalse(notesLiveData.hasObservers())
    }

}