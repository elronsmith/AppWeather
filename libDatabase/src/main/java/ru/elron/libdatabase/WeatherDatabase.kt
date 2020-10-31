package ru.elron.libdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [WeatherEntity::class], version = 1, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {
    companion object {
        private val DATABASE_NAME = "weather.db"
        fun getInstance(context: Context): WeatherDatabase {
            return Room.databaseBuilder(context, WeatherDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }

    abstract fun workDao(): WeatherDao
}
