package com.plcoding.cleanarchitecturenoteapp.feature_note.data.data_source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.plcoding.cleanarchitecturenoteapp.di.AppModule
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
@UninstallModules(AppModule::class)
class NoteDaoTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var database: NoteDatabase
    lateinit var noteDao: NoteDao

    private val testNote = Note("test-title", "test-content", 0L, 0, 1)

    @Before
    fun setUp() {
        hiltRule.inject()
        noteDao = database.noteDao
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertNote() = runBlockingTest {
        noteDao.insertNote(testNote)
        val allNotes = noteDao.getNotes().first()
        assertThat(allNotes).contains(testNote)
    }

    @Test
    fun getNoteById_returnsNote() = runBlockingTest {
        noteDao.insertNote(testNote)
        val note = noteDao.getNoteById(1)
        assertThat(note).isEqualTo(testNote)
    }

    @Test
    fun getNoteById_returnsNull() = runBlockingTest {
        noteDao.insertNote(testNote)
        val notExistedNote = noteDao.getNoteById(2)
        assertThat(notExistedNote).isNull()
    }

    @Test
    fun deleteNote() = runBlockingTest {
        noteDao.insertNote(testNote)
        noteDao.deleteNote(testNote)
        val allNotes = noteDao.getNotes().first()
        assertThat(allNotes).doesNotContain(testNote)
    }
}