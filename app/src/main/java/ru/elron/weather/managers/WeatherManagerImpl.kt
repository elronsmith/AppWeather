package ru.elron.weather.managers

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.elron.libcore.Weather
import ru.elron.libcore.base.SubscriberLiveData
import ru.elron.libdatabase.IDatabaseRepository
import ru.elron.libdatabase.WeatherEntity
import ru.elron.libnet.INetRepository
import ru.elron.weather.parsers.WeatherParser

class WeatherManagerImpl(
    val netRepository: INetRepository,
    val databaseRepository: IDatabaseRepository,
    val settings: ISettings
) : IWeatherManager {
    private val expired_time: Long = 10 * 60 * 1000

    private val getWeatherListLiveData = SubscriberLiveData<List<Weather>>()

    override fun getWeatherListAsync(): SubscriberLiveData<List<Weather>> {
        CoroutineScope(Dispatchers.IO).launch {
            getWeatherListLiveData.postValue(getWeatherList())
        }

        return getWeatherListLiveData
    }

    private fun getWeatherList(): List<Weather>? {
        val result = ArrayList<Weather>()

        var weatherEntityList = databaseRepository.getWeatherList()
        if (weatherEntityList.isEmpty()) {
            if (settings.isFirstRun()) {
                val list = ArrayList<WeatherEntity>()
                list.add(WeatherEntity(498817, "Санкт-Петербург", "", 1))
                list.add(WeatherEntity(524901, "Москва", "", 1))
                weatherEntityList = list
                settings.setFirstRun()
            } else {
                return result
            }
        }

        for (entity in weatherEntityList) {
            if (isExpired(entity)) {
                val pair = netRepository.getWeatherByCity(entity.city)
                if (pair.first != 200) continue

                val weather = WeatherParser.getWeatherOrNull(pair.second)
                if (weather?.city == null || weather.city!!.name == null) continue

                saveWeather(weather, pair.second!!)
                result.add(weather)
            } else {
                val weather = WeatherParser.getWeatherOrNull(entity.data)
                if (weather?.city == null || weather.city!!.name == null) continue

                result.add(weather)
            }
        }

        return result
    }

    private fun isExpired(entity: WeatherEntity): Boolean {
        return System.currentTimeMillis() > entity.date + expired_time
    }

    private fun saveWeather(weather: Weather, json: String) {
        databaseRepository.setWeather(WeatherEntity(
            weather.city!!.id,
            weather.city!!.name!!,
            json,
            System.currentTimeMillis()
        ))
    }

    interface ISettings {
        fun isFirstRun(): Boolean
        fun setFirstRun()
    }
}
