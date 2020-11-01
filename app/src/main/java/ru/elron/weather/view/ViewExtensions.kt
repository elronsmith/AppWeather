package ru.elron.weather.view

import androidx.fragment.app.Fragment
import ru.elron.weather.interfaces.ToolBarable

fun Fragment.setSubtitle(text: String? = null) {
    activity?.let {
        (activity as ToolBarable).setSubtitle(text)
    }
}

fun Fragment.setTitle(text: String? = null) {
    activity?.let {
        (activity as ToolBarable).setTitle(text)
    }
}
