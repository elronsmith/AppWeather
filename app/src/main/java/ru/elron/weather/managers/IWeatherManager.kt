package ru.elron.weather.managers

import ru.elron.libcore.Weather
import ru.elron.libcore.base.SubscriberLiveData

interface IWeatherManager {
    fun getWeatherListAsync(): SubscriberLiveData<List<Weather>>
}
