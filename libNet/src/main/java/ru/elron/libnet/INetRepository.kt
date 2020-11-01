package ru.elron.libnet

interface INetRepository {
    fun getWeatherByCity(city: String): Pair<Int, String?>
}
