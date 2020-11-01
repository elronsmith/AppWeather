package ru.elron.libdatabase

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseRepositoryImplTest {
    lateinit var database: WeatherDatabase
    lateinit var repository: DatabaseRepositoryImpl

    @Before
    fun beforeTest() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        if (!this::database.isInitialized) {
            database = WeatherDatabase.getInstance(appContext)
            repository = DatabaseRepositoryImpl.getInstance(database)
        }
        database.clearAllTables()
    }

    @Test
    fun getWeatherList_isCorrect() {
        Assert.assertEquals(0, repository.getSize())

        var list = repository.getWeatherList()
        Assert.assertNotNull(list)
        Assert.assertEquals(0, list.size)

        repository.setWeather(WeatherEntity(1, "Moscow", "", 1))
        list = repository.getWeatherList()
        Assert.assertNotNull(list)
        Assert.assertEquals(1, list.size)
        Assert.assertEquals(1, list[0].id)
        Assert.assertEquals("Moscow", list[0].city)

        repository.deleteWeatherByCity("Moscow")
        list = repository.getWeatherList()
        Assert.assertNotNull(list)
        Assert.assertEquals(0, list.size)
    }

    @Test
    fun getCityList_isCorrect() {
        Assert.assertEquals(0, repository.getSize())

        var list = repository.getCityList()
        Assert.assertNotNull(list)
        Assert.assertEquals(0, list.size)

        repository.setWeather(WeatherEntity(1, "Moscow", "data1", 1))
        repository.setWeather(WeatherEntity(2, "London", "data2", 2))

        list = repository.getCityList()
        Assert.assertNotNull(list)
        Assert.assertEquals(2, list.size)
    }

    @Test
    fun getWeather_isCorrect() {
        Assert.assertEquals(0, repository.getSize())

        var weather = repository.getWeather(1)
        Assert.assertNull(weather)
        weather = repository.getWeather("Moscow")
        Assert.assertNull(weather)

        repository.setWeather(WeatherEntity(1, "Moscow", "", 1))
        weather = repository.getWeather(1)
        Assert.assertNotNull(weather)
        Assert.assertEquals(1, weather!!.id)
        Assert.assertEquals("Moscow", weather.city)
        weather = repository.getWeather("Moscow")
        Assert.assertNotNull(weather)

        repository.deleteWeatherById(1)
        weather = repository.getWeather(1)
        Assert.assertNull(weather)
        weather = repository.getWeather("Moscow")
        Assert.assertNull(weather)
    }

    @Test
    fun setWeather_isCorrect() {
        Assert.assertEquals(0, repository.getSize())

        repository.setWeather(WeatherEntity(1, "Moscow", "data1", 1))
        var weather = repository.getWeather(1)
        Assert.assertNotNull(weather)
        Assert.assertEquals(1, weather!!.id)
        Assert.assertEquals("Moscow", weather.city)
        Assert.assertEquals("data1", weather.data)
        Assert.assertEquals(1, weather.date)

        repository.setWeather(WeatherEntity(1, "Moscow", "data2", 2))
        weather = repository.getWeather(1)
        Assert.assertNotNull(weather)
        Assert.assertEquals(1, weather!!.id)
        Assert.assertEquals("Moscow", weather.city)
        Assert.assertEquals("data2", weather.data)
        Assert.assertEquals(2, weather.date)
    }

    @Test
    fun deleteWeather_isCorrect() {
        Assert.assertEquals(0, repository.getSize())

        repository.setWeather(WeatherEntity(1, "Moscow", "data1", 1))
        repository.setWeather(WeatherEntity(2, "London", "data2", 2))
        val weather = repository.getWeather(1)
        Assert.assertNotNull(weather)

        repository.deleteWeather(weather!!)
        Assert.assertEquals(1, repository.getSize())
    }

    @Test
    fun deleteWeatherById_isCorrect() {
        Assert.assertEquals(0, repository.getSize())

        repository.setWeather(WeatherEntity(1, "Moscow", "", 1))
        repository.setWeather(WeatherEntity(2, "London", "data2", 2))
        Assert.assertEquals(2, repository.getSize())

        repository.deleteWeatherById(1)
        Assert.assertEquals(1, repository.getSize())
    }

    @Test
    fun deleteWeatherByCity_isCorrect() {
        Assert.assertEquals(0, repository.getSize())

        repository.setWeather(WeatherEntity(1, "Moscow", "", 1))
        repository.setWeather(WeatherEntity(2, "London", "data2", 2))
        Assert.assertEquals(2, repository.getSize())

        repository.deleteWeatherByCity("Moscow")
        Assert.assertEquals(1, repository.getSize())
    }

    @Test
    fun deleteAll_isCorrect() {
        Assert.assertEquals(0, repository.getSize())

        repository.setWeather(WeatherEntity(1, "Moscow", "", 1))
        repository.setWeather(WeatherEntity(2, "London", "data2", 2))
        Assert.assertEquals(2, repository.getSize())

        repository.deleteAll()
        Assert.assertEquals(0, repository.getSize())
    }
}