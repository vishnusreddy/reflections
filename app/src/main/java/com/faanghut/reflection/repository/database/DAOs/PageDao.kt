package com.faanghut.reflection.repository.database.DAOs

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.faanghut.reflection.models.Page
import com.faanghut.reflection.models.PageDate
import com.faanghut.reflection.models.PageDateWithPages
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface PageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg page: Page)

    @Transaction
    @Query("SELECT * FROM PageDate ORDER BY date DESC")
    fun getAllPageDatesWithPages(): Flow<List<PageDateWithPages>>

    @Transaction
    suspend fun insertPageWithDate(page: Page) {
        val existingPageDate = getPageDateByDate(page.date)
        if (existingPageDate == null) {
            val newPageDate = PageDate(page.date)
            insertPageDate(newPageDate)
        }
        insert(page)
    }

    @Query("SELECT * FROM PageDate WHERE date = :date")
    fun getPageDateByDate(date: LocalDate): PageDate?

    @Insert
    suspend fun insertPageDate(pageDate: PageDate)

    /*@Query("SELECT * FROM note ORDER BY id DESC")
    fun getAll(): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE date = :noteDate ORDER BY id DESC")
    fun getNotesOfDate(noteDate: LocalDate): List<Note>*/
}