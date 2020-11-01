package ru.elron.weather.managers

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.elron.libcore.Weather
import ru.elron.libcore.base.SubscriberLiveData

class FakeWeatherManagerImpl : IWeatherManager {
    private val getWeatherListLiveData = SubscriberLiveData<List<Weather>>()

    override fun getWeatherListAsync(): SubscriberLiveData<List<Weather>> {
        CoroutineScope(Dispatchers.IO).launch {
            getWeatherListLiveData.postValue(ArrayList<Weather>())
        }

        return getWeatherListLiveData
    }
}
