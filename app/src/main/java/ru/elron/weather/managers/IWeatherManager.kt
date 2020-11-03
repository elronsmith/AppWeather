package ru.elron.weather.managers

import ru.elron.libcore.Weather
import ru.elron.libcore.base.SingleLiveData
import ru.elron.libcore.base.SubscriberLiveData

interface IWeatherManager {
    fun requestGetWeatherListAsync(): SubscriberLiveData<List<Weather>>
    fun requestGetSearchByCityAsync(city: String): SingleLiveData<Weather>
    fun addCityAsync(city: String): SingleLiveData<Boolean>
    fun deleteCityAsync(city: String): SingleLiveData<Boolean>
    fun getWeatherByCityAsync(city: String): SingleLiveData<Weather>
}
