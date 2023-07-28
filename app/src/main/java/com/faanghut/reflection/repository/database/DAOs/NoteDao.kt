package com.faanghut.reflection.repository.database.DAOs

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.faanghut.reflection.models.Note
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(vararg notes: Note)

    @Update
    suspend fun updateNotes(vararg notes: Note)

    @Delete
    suspend fun deleteNotes(vararg notes: Note)

    @Query("SELECT * FROM note ORDER BY id DESC")
    fun getAll(): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE date = :noteDate ORDER BY id DESC")
    fun getNotesOfDate(noteDate: LocalDate): List<Note>

}