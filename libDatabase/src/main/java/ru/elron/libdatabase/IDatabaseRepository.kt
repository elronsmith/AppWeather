package ru.elron.libdatabase

interface IDatabaseRepository {
    fun getWeatherList(): List<WeatherEntity>
    fun getWeather(id: Long): WeatherEntity?
    fun getWeather(city: String): WeatherEntity?
    fun setWeather(weather: WeatherEntity)

    fun getCityList(): List<String>

    fun deleteWeather(weather: WeatherEntity)
    fun deleteWeatherById(id: Long)
    fun deleteWeatherByCity(city: String)
    fun deleteAll()

    fun getSize(): Int
}