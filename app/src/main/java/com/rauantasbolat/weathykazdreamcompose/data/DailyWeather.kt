package com.rauantasbolat.weathykazdreamcompose.data

data class DailyWeather(
    val day: String,
    val img: String,
    val weatherType: String,
    val maxTemp: String,
    val minTemp: String
)