package ru.elron.weather.ui.city

import android.app.Application
import android.os.Bundle
import android.view.View
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import ru.elron.libcore.Weather
import ru.elron.libcore.base.BaseViewModel
import ru.elron.libcore.base.SingleObserver
import ru.elron.weather.App
import ru.elron.weather.R
import ru.elron.weather.utils.WeatherHelper

class CityViewModel(application: Application, stateHandle: SavedStateHandle) :
    BaseViewModel<CityEntity, CityState, CityEvent>(
        application,
        stateHandle,
        "city_entity",
        CityState.Idle
    ) {

    private val manager = App.MANAGER
    private val stringBuilder = StringBuilder()

    private val forecastListener = View.OnClickListener {
        eventLiveData.postValue(CityEvent.ShowScreenForecast(entity.city.get()!!))
    }

    override fun getNewEntity(): CityEntity = CityEntity()

    override fun setupEntity() {
        entity.forecastListener = forecastListener
    }

    fun onCreateView(city: String) {
        entity.city.set(city)
    }

    fun requestGetWeather() {
        if (stateLiveData.value is CityState.Idle)
            requestGetWeatherForce()
    }

    private fun requestGetWeatherForce() {
        if (stateLiveData.value is CityState.Loading) return

        stateLiveData.value = CityState.Loading
        val observer: SingleObserver<Weather> = object : SingleObserver<Weather>() {
            override fun onChangedSingle(value: Weather) {
                unsubscribe()
                stateLiveData.value = CityState.Completed
                updateInfo(value)
            }
        }

        manager.getWeatherByCityAsync(entity.city.get()!!).observeForever(observer)
    }

    private fun updateInfo(weather: Weather) {
        entity.info.set(WeatherHelper.getInfo(weather, getApplication<App>().resources))
    }
}

class CityViewModelFactory(
    private val application: Application,
    private val owner: SavedStateRegistryOwner,
    private val defaultArgs: Bundle = Bundle()
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return CityViewModel(
            application,
            handle
        ) as T
    }
}
