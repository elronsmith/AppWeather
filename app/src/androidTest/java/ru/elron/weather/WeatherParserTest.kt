package ru.elron.weather

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import ru.elron.libnet.NetRepositoryImpl
import ru.elron.weather.parsers.WeatherParser

@RunWith(AndroidJUnit4::class)
class WeatherParserTest {
    @Test
    fun getWeatherOrNull_isCorrect() {
        val netRepository = NetRepositoryImpl()
        val pair = netRepository.getWeatherByCity("Москва")
        Assert.assertNotNull(pair)
        Assert.assertEquals(200, pair.first)
        Assert.assertNotNull(pair.second)

        val weather = WeatherParser.getWeatherOrNull(pair.second)
        Assert.assertNotNull(weather)
        Assert.assertNotNull(weather!!.list)
        Assert.assertNotNull(weather.city)
        Assert.assertTrue(weather.list!!.isNotEmpty())
    }
}
