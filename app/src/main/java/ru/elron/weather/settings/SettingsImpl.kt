package ru.elron.weather.settings

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class SettingsImpl(private val application: Application) : ISettings {
    private val FILENAME = "settings"

    private val PREF_FIRST_RUN = "FIRST_RUN"

    private val preferences: SharedPreferences = application.getSharedPreferences(FILENAME, Context.MODE_PRIVATE)
    override fun isFirstRun(): Boolean {
        return preferences.getBoolean(PREF_FIRST_RUN, true)
    }

    override fun setFirstRun() {
        preferences.edit()
            .putBoolean(PREF_FIRST_RUN, false)
            .apply()
    }
}
