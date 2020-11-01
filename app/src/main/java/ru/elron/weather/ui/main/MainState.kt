package ru.elron.weather.ui.main

import ru.elron.libcore.base.IState

sealed class MainState : IState {
    object Idle : MainState()
    object Loading : MainState()
    object Completed : MainState()
}
