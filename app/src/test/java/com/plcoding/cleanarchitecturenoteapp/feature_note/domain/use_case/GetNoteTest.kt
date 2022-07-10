package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case


import com.google.common.truth.Truth.assertThat
import com.plcoding.cleanarchitecturenoteapp.feature_note.data.repository.FakeNoteRepository
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetNoteTest {

    private lateinit var getNote: GetNote
    private lateinit var fakeRepository: FakeNoteRepository

    private val note1 = Note("test-title1", "test-content1", 0L, 0, 1)
    private val note2 = Note("test-title2", "test-content2", 0L, 0, 2)

    @Before
    fun setUp() {
        fakeRepository = FakeNoteRepository()
        getNote = GetNote(fakeRepository)
        runBlocking {
            fakeRepository.insertNote(note1)
            fakeRepository.insertNote(note2)
        }
    }

    @Test
    fun `getNote returns exact same note by id`() = runBlockingTest {
        val note = getNote(1)
        assertThat(note).isEqualTo(note1)
    }

    @Test
    fun `getNote returns null when there is no element with that id`() = runBlockingTest {
        val note = getNote(3)
        assertThat(note).isNull()
    }
}