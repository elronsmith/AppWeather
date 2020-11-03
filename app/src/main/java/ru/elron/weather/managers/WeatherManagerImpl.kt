package ru.elron.weather.managers

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.elron.libcore.Weather
import ru.elron.libcore.base.SingleLiveData
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
    private val getSearchByCityLiveData = SingleLiveData<Weather>()

    private val cache = HashMap<String, Pair<Weather, String>>()

    override fun requestGetWeatherListAsync(): SubscriberLiveData<List<Weather>> {
        CoroutineScope(Dispatchers.IO).launch {
            getWeatherListLiveData.postValue(requestGetWeatherList())
        }

        return getWeatherListLiveData
    }

    private fun requestGetWeatherList(): List<Weather>? {
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

    override fun requestGetSearchByCityAsync(city: String): SingleLiveData<Weather> {
        CoroutineScope(Dispatchers.IO).launch {
            getSearchByCityLiveData.postValue(requestGetSearchByCity(city))
        }

        return getSearchByCityLiveData
    }

    private fun requestGetSearchByCity(city: String): Weather {
        // отправляем запрос
        val pair = netRepository.getWeatherByCity(city)
        if (pair.first != 200) {
            return Weather.EMPTY
        }

        // парсим
        val weather = WeatherParser.getWeatherOrNull(pair.second)
        if (weather?.city == null || weather.city!!.name == null) {
            return Weather.EMPTY
        }

        // сохраняем в кеш
        cache[city] = Pair(weather, pair.second!!)

        return weather
    }

    override fun addCityAsync(city: String): SingleLiveData<Boolean> {
        val liveData = SingleLiveData<Boolean>()

        CoroutineScope(Dispatchers.IO).launch {
            liveData.postValue(addCity(city))
        }

        return liveData
    }

    private fun addCity(city: String): Boolean {
        val pair = cache[city]
        if (pair == null) return false

        databaseRepository.setWeather(obtainWeatherEntity(pair.first, pair.second))

        return true
    }

    override fun deleteCityAsync(city: String): SingleLiveData<Boolean> {
        val liveData = SingleLiveData<Boolean>()

        CoroutineScope(Dispatchers.IO).launch {
            liveData.postValue(deleteCity(city))
        }

        return liveData
    }

    private fun deleteCity(city: String): Boolean {
        databaseRepository.deleteWeatherByCity(city)
        return true
    }

    override fun getWeatherByCityAsync(city: String): SingleLiveData<Weather> {
        val liveData = SingleLiveData<Weather>()

        CoroutineScope(Dispatchers.IO).launch {
            val entity = databaseRepository.getWeather(city)

            val weather = WeatherParser.getWeatherOrNull(entity?.data)
            if (weather?.city == null || weather.city!!.name == null) {
                liveData.postValue(Weather.EMPTY)
            } else {
                liveData.postValue(weather)
            }
        }

        return liveData
    }

    private fun isExpired(entity: WeatherEntity): Boolean {
        return System.currentTimeMillis() > entity.date + expired_time
    }

    private fun saveWeather(weather: Weather, json: String) {
        databaseRepository.setWeather(obtainWeatherEntity(weather, json))
    }

    private fun obtainWeatherEntity(weather: Weather, json: String): WeatherEntity {
        return WeatherEntity(
            weather.city!!.id,
            weather.city!!.name!!,
            json,
            System.currentTimeMillis())
    }

    interface ISettings {
        fun isFirstRun(): Boolean
        fun setFirstRun()
    }
}
