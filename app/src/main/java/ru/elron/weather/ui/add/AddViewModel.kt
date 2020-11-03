package ru.elron.weather.ui.add

import android.app.Application
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import ru.elron.libcore.Weather
import ru.elron.libcore.base.BaseViewModel
import ru.elron.libcore.base.SingleObserver
import ru.elron.weather.App

class AddViewModel(application: Application, stateHandle: SavedStateHandle) :
    BaseViewModel<AddEntity, AddState, AddEvent>(
        application,
        stateHandle,
        "add_entity",
        AddState.Idle
    ) {
    private val manager = App.MANAGER
    private val searchListener = View.OnClickListener { searchByCity(entity.city.get()!!) }
    private val cityEditorListener = TextView.OnEditorActionListener {view, actionId, event ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            searchListener.onClick(view)
            return@OnEditorActionListener true
        }
        false
    }

    private val searchByCityObserver: SingleObserver<Weather> = object : SingleObserver<Weather>() {
        override fun onChangedSingle(value: Weather) {
            onSearchByCityResult(value)
        }
    }

    private fun searchByCity(city: String) {
        if (city.isEmpty()) {
            stateLiveData.postValue(AddState.ErrorCityEmpty)
        } else {
            requestSearchByCity(city)
        }
    }

    private fun requestSearchByCity(city: String) {
        entity.searchEnabled.set(false)
        entity.progressVisible.set(true)
        stateLiveData.value = AddState.Loading

        manager.requestGetSearchByCityAsync(city).observeForever(searchByCityObserver)
    }

    private fun onSearchByCityResult(weather: Weather) {
        entity.searchEnabled.set(true)
        entity.progressVisible.set(false)
        stateLiveData.value = AddState.Completed

        if (weather.hasCityName()) {
            eventLiveData.postValue(AddEvent.ShowScreenAddList(weather.city!!.name!!))
        } else {
            eventLiveData.postValue(AddEvent.ShowDialogErrorCityNotFound(entity.city.get()!!))
        }
    }

    override fun getNewEntity(): AddEntity = AddEntity()

    override fun setupEntity() {
        entity.searchListener = searchListener
        entity.cityEditorListener = cityEditorListener
    }

    override fun onCleared() {
        searchByCityObserver.unsubscribe()
        super.onCleared()
    }
}

class AddViewModelFactory(
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
        return AddViewModel(
            application,
            handle
        ) as T
    }
}
