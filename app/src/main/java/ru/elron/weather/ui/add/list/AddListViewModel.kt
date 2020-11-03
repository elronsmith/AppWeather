package ru.elron.weather.ui.add.list

import android.app.Application
import android.os.Bundle
import android.view.View
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import ru.elron.libcore.base.BaseViewModel
import ru.elron.libcore.base.SingleObserver
import ru.elron.weather.App

class AddListViewModel(application: Application, stateHandle: SavedStateHandle) :
    BaseViewModel<AddListEntity, AddListState, AddListEvent>(
        application,
        stateHandle,
        "add_list_entity",
        AddListState.Idle
    ) {

    private val manager = App.MANAGER
    private val addListener = View.OnClickListener { addCityAsync() }

    private val addCityObserver: SingleObserver<Boolean> = object : SingleObserver<Boolean>() {
        override fun onChangedSingle(value: Boolean) {
            unsubscribe()
            entity.addEnabled.set(true)
            if (value) {
                eventLiveData.postValue(AddListEvent.OnAddItemSuccess)
            } else {
                eventLiveData.postValue(AddListEvent.OnAddItemError)
            }
        }
    }

    private fun addCityAsync() {
        entity.addEnabled.set(false)
        manager.addCityAsync(entity.city.get()!!).observeForever(addCityObserver)
    }

    override fun getNewEntity(): AddListEntity = AddListEntity()

    override fun setupEntity() {
        entity.addListener = addListener
    }

    fun onCreateView(city: String) {
        entity.city.set(city)
    }

    override fun onCleared() {
        addCityObserver.unsubscribe()
        super.onCleared()
    }
}

class AddListViewModelFactory(
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
        return AddListViewModel(
            application,
            handle
        ) as T
    }
}
