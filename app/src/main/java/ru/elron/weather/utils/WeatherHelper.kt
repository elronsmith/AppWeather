package ru.elron.weather.utils

import android.content.res.Resources
import ru.elron.libcore.Weather
import ru.elron.weather.R
import java.text.SimpleDateFormat
import java.util.*

object WeatherHelper {
    private val stringBuilder = StringBuilder()
    private val calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    private val timeFormat = SimpleDateFormat("kk:mm", Locale.getDefault())

    fun getSize(weather: Weather): Int {
        if (weather.list == null) return 0
        return weather.list!!.size
    }

    fun getInfo(weather: Weather, resources: Resources): String {
        stringBuilder.setLength(0)

        if (getSize(weather) > 0) {
            return getInfo(weather, resources, 0)
        } else {
            stringBuilder.append(resources.getString(R.string.info_null))
        }

        return stringBuilder.toString()
    }

    fun getInfo(weather: Weather, resources: Resources, index: Int): String {
        stringBuilder.setLength(0)

        val item = weather.list!![index]
        val temp = item.main?.temp?.toInt() ?: 99
        calendar.timeInMillis = item.dt * 1000

        stringBuilder.append(dateFormat.format(calendar.time))
            .append("\n")
            .append(timeFormat.format(calendar.time))
            .append("\n")
            .append(resources.getString(R.string.info_temp, temp))
            .append("\n")
            .append(resources.getString(R.string.info_pressure, item.main?.pressure))
            .append("\n")
            .append(resources.getString(R.string.info_humidity, item.main?.humidity))
            .append("\n")

        val speed = item.wind?.speed?.toInt() ?: 0
        stringBuilder.append(resources.getString(R.string.info_speed, speed))
            .append("\n")
            .append(resources.getString(R.string.info_deg, item.wind?.deg))


        return stringBuilder.toString()
    }
}
