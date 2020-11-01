package ru.elron.libcore.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle

/**
 * Абстрактная базовая ViewModel, которая хранит в себе состояния на случай поворота экрана.
 * Создаётся либо в Activity либо в Fragment'e.
 * Создаётся через класс [AbstractSavedStateViewModelFactory]
 *
 * Дженерики:
 * ENTITY   isDefault
 * STATE    Nothing вместо null
 * EVENT?   nullable
 *
 * STATE сохраняется если открыть следующий фрагмент и вернуться назад
 */
abstract class BaseViewModel<ENTITY : AEntity, STATE : IState, EVENT : IEvent>(
    application: Application,
    protected val stateHandle: SavedStateHandle,
    /** Уникальный ключ для текущей ViewModel */
    protected val argEntity: String,
    /** Состояние с которого открывается текущий экран */
    protected val defaultState: STATE
) : AndroidViewModel(application) {
    /**
     * Сущность которая хранит в себе данные, которые отображаются во View
     */
    val entity: ENTITY = stateHandle.get<ENTITY>(argEntity) ?: getNewEntity()

    /**
     * Хранит состояние, всегда не null
     */
    val stateLiveData = MutableLiveData<STATE>(defaultState)

    /**
     * Разовые события для View, например открыть экран, показать диалог.
     * После того как событие будет обработано событие само автоматически удаляется.
     */
    val eventLiveData = SingleLiveData<EVENT?>()

    /**
     * Вызывается в момент создания Activity или Fragment'a
     */
    open fun onCreateView() { }

    open fun saveCurrentState() {
        stateHandle.set(argEntity, entity)
    }

    /**
     * Загружает данные в [AEntity]
     */
    open fun setupEntity() { }

    /**
     * Возвращает новый экземпляр сущности. Вызывается во время создания ViewModel
     */
    abstract fun getNewEntity(): ENTITY
}
