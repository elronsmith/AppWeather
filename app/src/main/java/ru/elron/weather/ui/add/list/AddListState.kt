package ru.elron.weather.ui.add.list

import ru.elron.libcore.base.IState

sealed class AddListState : IState {
    object Idle : AddListState()
}
