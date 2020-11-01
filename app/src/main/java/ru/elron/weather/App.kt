package ru.elron.weather

import android.app.Application
import ru.elron.libdatabase.DatabaseRepositoryImpl
import ru.elron.libdatabase.IDatabaseRepository
import ru.elron.libdatabase.WeatherDatabase
import ru.elron.libnet.INetRepository
import ru.elron.libnet.NetRepositoryImpl
import ru.elron.weather.managers.FakeWeatherManagerImpl
import ru.elron.weather.managers.IWeatherManager
import ru.elron.weather.managers.WeatherManagerImpl
import ru.elron.weather.settings.ISettings
import ru.elron.weather.settings.SettingsImpl

class App : Application() {
    companion object{
        public lateinit var INSTANCE: App
        public lateinit var SETTINGS: ISettings

        private lateinit var weatherDatabase: WeatherDatabase
        public lateinit var MANAGER: IWeatherManager
    }
    
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        SETTINGS = SettingsImpl(this)
        weatherDatabase = WeatherDatabase.getInstance(this)
        MANAGER = WeatherManagerImpl(
            NetRepositoryImpl(),
            DatabaseRepositoryImpl.getInstance(weatherDatabase),
            SETTINGS)
//        MANAGER = FakeWeatherManagerImpl()
    }
}
