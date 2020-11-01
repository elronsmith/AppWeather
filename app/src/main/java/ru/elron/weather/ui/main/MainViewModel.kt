package ru.elron.weather.ui.main

import android.app.Application
import android.os.Bundle
import android.view.View
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import ru.elron.libcore.Weather
import ru.elron.libcore.base.BaseViewModel
import ru.elron.libcore.base.SubscriberObserver
import ru.elron.weather.App
import ru.elron.weather.R
import ru.elron.weather.observables.MainItemObservable
import ru.elron.weather.observables.MainItemViewHolder
import ru.elron.weather.view.AObservable
import ru.elron.weather.view.OnLongItemClickViewHolderCallback
import ru.elron.weather.view.RecyclerAdapter

class MainViewModel(val app: Application, stateHandle: SavedStateHandle) :
    BaseViewModel<MainEntity, MainState, MainEvent>(
        app,
        stateHandle,
        "main_entity",
        MainState.Idle
    ), OnLongItemClickViewHolderCallback {

    private val manager = App.MANAGER

    val adapter: RecyclerAdapter<AObservable> = RecyclerAdapter()
    private val addListener = View.OnClickListener {
        eventLiveData.postValue(MainEvent.ShowScreenAdd)
    }

    private val getWeatherListObserver: SubscriberObserver<List<Weather>> = object : SubscriberObserver<List<Weather>>() {
        override fun onChanged(responce: List<Weather>?) {
            if (responce == null) return
            onGetWeatherListResult(responce)
        }
    }

    init {
        MainItemViewHolder.addViewHolder(adapter.holderBuilderArray, this)
    }

    override fun getNewEntity(): MainEntity = MainEntity()

    override fun setupEntity() {
        entity.addListener = addListener
    }

    override fun onLongItemClick(v: View?, observable: AObservable, position: Int) {
        eventLiveData.postValue(MainEvent.ShowDialogDeleteItem((observable as MainItemObservable).city))
    }

    override fun onItemClick(v: View?, observable: AObservable, position: Int) {
        eventLiveData.postValue(MainEvent.ShowScreenCity((observable as MainItemObservable).city))
    }

    override fun getObservable(position: Int): AObservable = adapter.observableList[position]

    override fun onCleared() {
        getWeatherListObserver.unsubscribe()
        super.onCleared()
    }

    fun updateCities() {

    }

    fun requestGetWeather() {
        if (stateLiveData.value is MainState.Completed) return
        requestGetWeatherForce()
    }

    private fun requestGetWeatherForce() {
        if (stateLiveData.value is MainState.Completed) return

        stateLiveData.value = MainState.Loading
        entity.progressVisible.set(true)
        manager.getWeatherListAsync().observeForever(getWeatherListObserver)
    }

    private fun onGetWeatherListResult(list: List<Weather>) {
        entity.progressVisible.set(false)
        stateLiveData.value = MainState.Completed
        adapter.observableList.clear()

        for (weather in list) {
            addWeather(weather)
        }

        adapter.notifyDataSetChanged()
    }

    private fun addWeather(weather: Weather) {
        val city = weather.city?.name ?: "??"
        val degree = weather.list?.get(0)?.main?.temp
        val degreeString = if (degree == null) "??"
            else getApplication<App>().getString(R.string.degrees, degree.toInt())
        adapter.observableList.add(MainItemObservable(
            city,
            degreeString
        ))
    }
}

class MainViewModelFactory(
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
        return MainViewModel(
            application,
            handle
        ) as T
    }
}
