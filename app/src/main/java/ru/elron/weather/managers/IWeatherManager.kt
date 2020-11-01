package ru.elron.weather.managers

import ru.elron.libcore.Weather
import ru.elron.libcore.base.SingleLiveData
import ru.elron.libcore.base.SubscriberLiveData

interface IWeatherManager {
    fun getWeatherListAsync(): SubscriberLiveData<List<Weather>>
    fun getSearchByCityAsync(city: String): SubscriberLiveData<Weather>
    fun addCityAsync(): SingleLiveData<Boolean>
    fun deleteCityAsync(city: String): SingleLiveData<Boolean>
}
