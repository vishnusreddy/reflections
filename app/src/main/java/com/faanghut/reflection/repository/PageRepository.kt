package com.faanghut.reflection.repository

import androidx.annotation.WorkerThread
import com.faanghut.reflection.models.Page
import com.faanghut.reflection.models.PageDateWithPages
import com.faanghut.reflection.repository.database.DAOs.PageDao
import kotlinx.coroutines.flow.Flow

class PageRepository(private val pageDao: PageDao) {

    val allPagesGroupedByDate: Flow<List<PageDateWithPages>> = pageDao.getAllPageDatesWithPages()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(page: Page) {
        pageDao.insertPageWithDate(page)
    }

}