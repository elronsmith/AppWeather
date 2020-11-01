package ru.elron.weather.ui.add.list

import ru.elron.libcore.base.IEvent

sealed class AddListEvent : IEvent {
    object OnAddItemSuccess: AddListEvent()
    object OnAddItemError: AddListEvent()
}
