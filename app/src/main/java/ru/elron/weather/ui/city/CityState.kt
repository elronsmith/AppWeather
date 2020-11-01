package ru.elron.weather.ui.city

import ru.elron.libcore.base.IState

sealed class CityState : IState {
    object Idle : CityState()
    object Loading : CityState()
    object Completed : CityState()
}
