package com.faanghut.reflection.repository.database.DAOs

import androidx.room.Dao
import androidx.room.Delete
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

    @Delete
    fun delete(page: Page)

    @Query("SELECT * FROM Page WHERE date = :date")
    fun getPagesByDate(date: LocalDate): List<Page>

    @Transaction
    suspend fun deletePage(page: Page) {
        val pagesForDate = getPagesByDate(page.date)
        if (pagesForDate.size == 1) {
            // This is the only Page for the date, so we can delete the associated PageDate
            val pageDate = getPageDateByDate(page.date)
            pageDate?.let { deletePageDate(it) }
        }
        delete(page)
    }

    @Delete
    suspend fun deletePageDate(pageDate: PageDate)

    // This would be required later on if needed for querying by a single date.
    /*@Query("SELECT * FROM note ORDER BY id DESC")
    fun getAll(): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE date = :noteDate ORDER BY id DESC")
    fun getNotesOfDate(noteDate: LocalDate): List<Note>*/
}