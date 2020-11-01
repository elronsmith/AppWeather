package ru.elron.weather.ui.city.forecast

import ru.elron.libcore.base.IState

sealed class ForecastState : IState {
    object Idle : ForecastState()
    object Loading : ForecastState()
    object Completed : ForecastState()
}
