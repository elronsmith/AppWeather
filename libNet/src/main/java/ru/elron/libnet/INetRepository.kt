package ru.elron.libnet

import java.io.IOException

interface INetRepository {
    @Throws(IOException::class)
    fun getWeatherByCity(city: String): Pair<Int, String?>?
}