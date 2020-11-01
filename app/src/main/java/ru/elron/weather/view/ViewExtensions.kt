package ru.elron.weather.view

import androidx.appcompat.app.AppCompatActivity
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

fun Fragment.showButtonBack() {
    activity?.let {
        (it as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)
        it.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
fun Fragment.hideButtonBack() {
    activity?.let {
        (it as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(false)
        it.supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}
