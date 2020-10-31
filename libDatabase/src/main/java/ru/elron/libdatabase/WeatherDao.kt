package ru.elron.libdatabase

import androidx.room.*

@Dao
abstract class WeatherDao {
    @Query("SELECT * FROM ${WeatherEntity.TABLE_NAME}")
    abstract fun getWeatherList(): List<WeatherEntity>

    @Query("SELECT * FROM ${WeatherEntity.TABLE_NAME} WHERE id = :id")
    abstract fun getWeather(id: Long): WeatherEntity?

    @Query("SELECT * FROM ${WeatherEntity.TABLE_NAME} WHERE city LIKE :city")
    abstract fun getWeather(city: String): WeatherEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun setWeather(weather: WeatherEntity)

    @Delete
    abstract fun deleteWeather(weather: WeatherEntity)
    @Query("DELETE FROM ${WeatherEntity.TABLE_NAME} WHERE id = :id")
    abstract fun deleteWeatherById(id: Long)
    @Query("DELETE FROM ${WeatherEntity.TABLE_NAME} WHERE city LIKE :city")
    abstract fun deleteWeatherByCity(city: String)
    @Query("DELETE FROM ${WeatherEntity.TABLE_NAME}")
    abstract fun deleteAll()

    @Query("SELECT COUNT(id) FROM ${WeatherEntity.TABLE_NAME}")
    abstract fun getSize(): Int
}
