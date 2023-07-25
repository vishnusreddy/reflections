package com.faanghut.reflection

import android.app.Application

class ReflectionApplication: Application() {

    companion object {
        private lateinit var instance: ReflectionApplication

        fun getInstance(): ReflectionApplication {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}