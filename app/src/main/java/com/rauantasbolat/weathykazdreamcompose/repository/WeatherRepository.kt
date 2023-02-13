package com.rauantasbolat.weathykazdreamcompose.repository

import com.rauantasbolat.weathykazdreamcompose.core.constants.Constants.API_KEY
import com.rauantasbolat.weathykazdreamcompose.data.WeatherData
import com.rauantasbolat.weathykazdreamcompose.network.Api
import com.rauantasbolat.weathykazdreamcompose.core.utils.Handler
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class WeatherRepository @Inject constructor(
    private val api: Api
) {
    suspend fun getWeather(
        lat: String,
        lon: String
    ): Handler<WeatherData> {
        val response = try{
            api.getWeather(lat, lon, API_KEY)
        } catch (e: Exception) {
            return Handler.Error("Error")
        }
        return Handler.Success(response)
    }
}