package ru.elron.libnet

import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request

class NetRepositoryImpl : INetRepository {
    private val MAIN_URL = "http://api.openweathermap.org"
    private val FORECAST_PATH = "/data/2.5/forecast"

    private val PARAM_Q = "q"
    private val PARAM_APPID = "appid"
    private val PARAM_LANG = "lang"
    private val PARAM_UNITS = "units"
    private val PARAM_EXCLUDE = "exclude"

    private val client = OkHttpClient()
    private val forecastUrlBuilder = "$MAIN_URL$FORECAST_PATH".toHttpUrlOrNull()!!.newBuilder()

    override fun getWeatherByCity(city: String): Pair<Int, String?>? {
        forecastUrlBuilder.setEncodedQueryParameter(PARAM_Q, city)
            .addEncodedQueryParameter(PARAM_APPID, "3aec242b4db00a9092b74b2233a86a4d")
            .addEncodedQueryParameter(PARAM_LANG, "ru")
            .addEncodedQueryParameter(PARAM_UNITS, "metric")
            .addEncodedQueryParameter(PARAM_EXCLUDE, "minutely,hourly")
        val url = forecastUrlBuilder.build()

        val request = Request.Builder()
            .url(url)
            .build()

        val call = client.newCall(request)
        val responce = call.execute()
        val json: String? = responce.body?.string()

        return Pair(responce.code, json)
    }
}