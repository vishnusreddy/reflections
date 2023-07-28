package com.faanghut.reflection

import android.app.Application
import com.faanghut.reflection.repository.NoteRepository
import com.faanghut.reflection.repository.database.AppDatabase
import com.google.android.material.color.DynamicColors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ReflectionApplication: Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    private val database by lazy { AppDatabase.getDatabase(this, applicationScope) }
    val noteRepository by lazy { NoteRepository(database.noteDao()) }

    companion object {
        private lateinit var instance: ReflectionApplication

        fun getInstance(): ReflectionApplication {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}