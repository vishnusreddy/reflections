package com.faanghut.reflection.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.faanghut.reflection.models.Page
import com.faanghut.reflection.models.PageDate
import com.faanghut.reflection.repository.database.DAOs.PageDao
import com.faanghut.reflection.repository.database.converters.Converters
import kotlinx.coroutines.CoroutineScope

@Database(entities = [PageDate::class, Page::class], version = 1)
@TypeConverters(
    Converters::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun pageDateDao(): PageDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context, scope: CoroutineScope
        ): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java, "reflection-notes-db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}