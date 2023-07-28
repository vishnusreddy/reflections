package com.faanghut.reflection.repository

import androidx.annotation.WorkerThread
import com.faanghut.reflection.models.Note
import com.faanghut.reflection.repository.database.DAOs.NoteDao
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {

    val allNotes: Flow<List<Note>> = noteDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(note: Note) {
        noteDao.insertNotes(note)
    }

}