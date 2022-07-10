package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.plcoding.cleanarchitecturenoteapp.feature_note.data.repository.FakeNoteRepository
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.InvalidNoteException
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AddNoteTest {
    private lateinit var addNote: AddNote
    private lateinit var fakeRepository: NoteRepository

    @Before
    fun setUp() {
        fakeRepository = FakeNoteRepository()
        addNote = AddNote(fakeRepository)
    }

    @Test
    fun `adding note with empty title throws InvalidNoteException`() = runBlockingTest {
        val note = Note("", "content", 0L, 0)
        try {
            addNote(note)
        } catch (e: Exception) {
            assertThat(e).isInstanceOf(InvalidNoteException::class.java)
        }
    }

    @Test
    fun `adding note with empty content throws InvalidNoteException`() = runBlockingTest {
        val note = Note("title", "", 0L, 0)
        try {
            addNote(note)
        } catch (e: Exception) {
            assertThat(e).isInstanceOf(InvalidNoteException::class.java)
        }
    }

    @Test
    fun `adding note with non empty title and content actually adds`() = runBlockingTest {
        val note = Note("title", "content", 0L, 0)
        addNote(note)
        assertThat(fakeRepository.getNotes().first()).contains(note)
    }
}