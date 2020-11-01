package ru.elron.weather.ui.add

import ru.elron.libcore.base.IEvent

sealed class AddEvent : IEvent {
    data class ShowScreenAddList(val city: String): AddEvent()
    data class ShowDialogErrorCityNotFound(val city: String): AddEvent()
}
