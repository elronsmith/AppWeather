package ru.elron.weather.ui.city.forecast

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
import ru.elron.weather.observables.CityForecastItemObservable
import ru.elron.weather.observables.CityForecastItemViewHolder
import ru.elron.weather.utils.WeatherHelper
import ru.elron.weather.view.AObservable
import ru.elron.weather.view.OnItemClickViewHolderCallback
import ru.elron.weather.view.RecyclerAdapter

class ForecastViewModel(val app: Application, stateHandle: SavedStateHandle) :
    BaseViewModel<ForecastEntity, ForecastState, ForecastEvent>(
        app,
        stateHandle,
        "forecast_entity",
        ForecastState.Idle
    ), OnItemClickViewHolderCallback {

    private val manager = App.MANAGER
    val adapter: RecyclerAdapter<AObservable> = RecyclerAdapter()

    init {
        CityForecastItemViewHolder.addViewHolder(adapter.holderBuilderArray, this)
    }

    override fun getNewEntity(): ForecastEntity = ForecastEntity()

    override fun onItemClick(v: View?, observable: AObservable, position: Int) { }

    override fun getObservable(position: Int): AObservable = adapter.observableList[position]

    fun onCreateView(city: String) {
        entity.city.set(city)
    }

    fun requestGetWeather() {
        if (stateLiveData.value is ForecastState.Idle)
            requestGetWeatherForce()
    }

    private fun requestGetWeatherForce() {
        if (stateLiveData.value is ForecastState.Loading) return

        stateLiveData.value = ForecastState.Loading
        val observer: SingleObserver<Weather> = object : SingleObserver<Weather>() {
            override fun onChangedSingle(value: Weather) {
                unsubscribe()
                stateLiveData.value = ForecastState.Completed
                updateInfo(value)
            }
        }

        manager.getWeatherByCityAsync(entity.city.get()!!).observeForever(observer)
    }

    private fun updateInfo(weather: Weather) {
        val size = WeatherHelper.getSize(weather)
        if (size == 0) {
            adapter.observableList.clear()
            adapter.notifyDataSetChanged()
        } else {
            val resources = getApplication<App>().resources
            for (index in 0 until size) {
                val info = WeatherHelper.getInfo(weather, resources, index)
                val observable = CityForecastItemObservable(info)
                adapter.observableList.add(observable)
            }
            adapter.notifyDataSetChanged()
        }
    }
}

class ForecastViewModelFactory(
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
        return ForecastViewModel(
            application,
            handle
        ) as T
    }
}
