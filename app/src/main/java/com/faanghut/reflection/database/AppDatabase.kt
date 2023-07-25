package com.faanghut.reflection.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.faanghut.reflection.ReflectionApplication
import com.faanghut.reflection.database.DAOs.NoteDao
import com.faanghut.reflection.models.Note

@Database(entities = [Note::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
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