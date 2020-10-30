package ru.elron.weather

import android.app.Application

class App : Application() {
    companion object{
        public lateinit var INSTANCE: App
    }
    
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}
