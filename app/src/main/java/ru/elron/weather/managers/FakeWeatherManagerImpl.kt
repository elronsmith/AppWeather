package ru.elron.weather.managers

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.elron.libcore.City
import ru.elron.libcore.Weather
import ru.elron.libcore.base.SingleLiveData
import ru.elron.libcore.base.SubscriberLiveData

class FakeWeatherManagerImpl : IWeatherManager {
    private val getWeatherListLiveData = SubscriberLiveData<List<Weather>>()
    private val getSearchByCityLiveData = SubscriberLiveData<Weather>()

    override fun getWeatherListAsync(): SubscriberLiveData<List<Weather>> {
        CoroutineScope(Dispatchers.IO).launch {
            getWeatherListLiveData.postValue(ArrayList<Weather>())
        }

        return getWeatherListLiveData
    }

    override fun getSearchByCityAsync(city: String): SubscriberLiveData<Weather> {
        CoroutineScope(Dispatchers.IO).launch {
            val weather = Weather()
            weather.city = City()
            weather.city!!.name = city
            getSearchByCityLiveData.postValue(weather)
        }

        return getSearchByCityLiveData
    }

    override fun addCityAsync(): SingleLiveData<Boolean> {
        val liveData = SingleLiveData<Boolean>()

        CoroutineScope(Dispatchers.IO).launch {
            liveData.postValue(true)
        }

        return liveData
    }

    override fun deleteCityAsync(city: String): SingleLiveData<Boolean> {
        val liveData = SingleLiveData<Boolean>()

        CoroutineScope(Dispatchers.IO).launch {
            liveData.postValue(true)
        }

        return liveData
    }

    override fun getWeatherByCityAsync(city: String): SingleLiveData<Weather> {
        val liveData = SingleLiveData<Weather>()

        CoroutineScope(Dispatchers.IO).launch {
            val weather = Weather()
            weather.city = City()
            weather.city!!.name = city
            liveData.postValue(weather)
        }

        return liveData
    }
}
