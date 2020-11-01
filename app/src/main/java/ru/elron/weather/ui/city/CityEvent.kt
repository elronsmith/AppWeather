package ru.elron.weather.ui.city

import ru.elron.libcore.base.IEvent

sealed class CityEvent : IEvent {
    data class ShowScreenForecast(val city: String): CityEvent()
}
