package ru.elron.libnet

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NetRepositoryImplTest {
    @Test
    fun getWeatherByCity_isCorrect() {
        val netRepository = NetRepositoryImpl()

        var pair = netRepository.getWeatherByCity("Москва")
        Assert.assertNotNull(pair)
        Assert.assertEquals(200, pair!!.first)
        Assert.assertNotNull(pair.second)

        pair = netRepository.getWeatherByCity("Мск")
        Assert.assertNotNull(pair)
        Assert.assertEquals(404, pair!!.first)
        Assert.assertNotNull(pair.second)
    }

}