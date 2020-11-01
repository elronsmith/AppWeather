package ru.elron.weather.ui.main

import ru.elron.libcore.base.IEvent


sealed class MainEvent : IEvent {
    object ShowScreenAdd: MainEvent()
    data class ShowScreenCity(val city: String): MainEvent()
    data class ShowDialogDeleteItem(val city: String, val index: Int): MainEvent()
    object ShowToastDeleteOk: MainEvent()
}
