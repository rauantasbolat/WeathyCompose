package com.rauantasbolat.weathykazdreamcompose.network

import com.rauantasbolat.weathykazdreamcompose.data.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("/data/2.5/forecast?")
    suspend fun getWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String,
        @Query("exclude") exclude: String = "minutely, alerts, hourly"
    ) : WeatherData
}