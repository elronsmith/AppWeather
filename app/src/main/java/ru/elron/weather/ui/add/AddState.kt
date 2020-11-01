package ru.elron.weather.ui.add

import ru.elron.libcore.base.IState

sealed class AddState : IState {
    object Idle : AddState()
    object ErrorCityEmpty : AddState()
    object Loading : AddState()
    object Completed : AddState()
}
