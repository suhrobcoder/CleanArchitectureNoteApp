package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.plcoding.cleanarchitecturenoteapp.feature_note.data.repository.FakeNoteRepository
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DeleteNoteTest {

    private lateinit var deleteNote: DeleteNote
    private lateinit var fakeRepository: FakeNoteRepository

    private val note1 = Note("test-title1", "test-content1", 0L, 0, 1)

    @Before
    fun setUp() {
        fakeRepository = FakeNoteRepository()
        deleteNote = DeleteNote(fakeRepository)
        runBlocking {
            fakeRepository.insertNote(note1)
        }
    }

    @Test
    fun `DeleteNote removes note`() = runBlockingTest {
        deleteNote(note1)
        assertThat(fakeRepository.getNotes().first()).doesNotContain(note1)
    }
}