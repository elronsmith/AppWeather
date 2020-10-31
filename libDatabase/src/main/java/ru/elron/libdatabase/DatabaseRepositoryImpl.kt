package ru.elron.libdatabase

class DatabaseRepositoryImpl(private val weatherDao: WeatherDao) : IDatabaseRepository {
    companion object {
        fun getInstance(database: WeatherDatabase): DatabaseRepositoryImpl {
            return DatabaseRepositoryImpl(database.workDao())
        }
    }

    override fun getWeatherList(): List<WeatherEntity> {
        return weatherDao.getWeatherList()
    }

    override fun getWeather(id: Long): WeatherEntity? {
        return weatherDao.getWeather(id)
    }

    override fun getWeather(city: String): WeatherEntity? {
        return weatherDao.getWeather(city)
    }

    override fun setWeather(weather: WeatherEntity) {
        weatherDao.setWeather(weather)
    }

    override fun deleteWeather(weather: WeatherEntity) {
        weatherDao.deleteWeather(weather)
    }

    override fun deleteWeatherById(id: Long) {
        weatherDao.deleteWeatherById(id)
    }

    override fun deleteWeatherByCity(city: String) {
        weatherDao.deleteWeatherByCity(city)
    }

    override fun deleteAll() {
        weatherDao.deleteAll()
    }

    override fun getSize(): Int {
        return weatherDao.getSize()
    }

}