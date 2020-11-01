package ru.elron.weather.parsers

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import ru.elron.libcore.Weather

object WeatherParser {
    private val gson = GsonBuilder().create()

    fun getWeatherOrNull(json: String?): Weather? {
        if (json == null) return null
        var result: Weather? = null
        try {
            result = gson.fromJson(json, object : TypeToken<Weather>(){}.type)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result
    }
}