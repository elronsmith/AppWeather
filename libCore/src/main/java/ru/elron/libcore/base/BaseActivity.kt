package ru.elron.libcore.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

/**
 * Абстрактный базовый Activity для связи с BaseViewModel
 */
abstract class BaseActivity<ENTITY : AEntity, STATE : IState, EVENT : IEvent>() : AppCompatActivity() {
    protected val stateObserver = Observer<STATE> { state ->
        onState(state)
    }
    protected val eventObserver: SingleObserver<EVENT?> = object : SingleObserver<EVENT?>() {
        override fun onChangedSingle(value: EVENT?) {
            onEvent(value!!)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getMainViewModel().setupEntity()
        getMainViewModel().onCreateView()
    }

    override fun onStart() {
        super.onStart()
        getMainViewModel().stateLiveData.observe(this, stateObserver)
        getMainViewModel().eventLiveData.observe(this, eventObserver)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        getMainViewModel().saveCurrentState()
    }

    /**
     * Вызывается когда изменилось состояние
     */
    open fun onState(state: STATE) {}

    /**
     * Вызывается когда пришло событие, например открыть экран
     */
    open fun onEvent(event: EVENT) {}

    abstract fun getMainViewModel(): BaseViewModel<ENTITY, STATE, EVENT>
}