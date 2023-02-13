package com.rauantasbolat.weathykazdreamcompose

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rauantasbolat.weathykazdreamcompose.core.utils.*
import com.rauantasbolat.weathykazdreamcompose.data.DailyWeather
import com.rauantasbolat.weathykazdreamcompose.data.HourlyWeather
import com.rauantasbolat.weathykazdreamcompose.data.SearchWidgetState
import com.rauantasbolat.weathykazdreamcompose.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: WeatherRepository
) : ViewModel() {

    var isLoading = mutableStateOf(false)
    var loadError = mutableStateOf("")
    var hourlyWeather = mutableStateOf<List<HourlyWeather>>(listOf())
    var currentDate = mutableStateOf("")
    var currentType = mutableStateOf("")
    var currentTemperature = mutableStateOf("")
    var currentIcon = mutableStateOf("")
    var currentHumidity = mutableStateOf("")
    var currentUV = mutableStateOf("")
    var dailyWeatherList = mutableStateOf<List<DailyWeather>>(listOf())

    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(value = SearchWidgetState.CLOSED)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState

    private val _searchTextState: MutableState<String> =
        mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }





    fun currentWeather(lat: String, lon: String){
        viewModelScope.launch {
            isLoading.value = false
            when (val result = repo.getWeather(lat, lon)) {
                is Handler.Success -> {
                    currentDate.value = getFormattedDate(result.data!!.current.dt)
                    currentType.value = result.data.current.weather[0].main
                    currentTemperature.value = getTemperature(result.data.current.temp)
                    currentIcon.value = result.data.current.weather[0].icon
                    currentHumidity.value = (result.data.current.humidity).toString()
                    currentUV.value = getFormattedUVRange(result.data.current.uvi)
                }
            }
        }
    }

    fun dailyWeather(lat: String, lon: String) {
        viewModelScope.launch {
            isLoading.value = true
            when (val result = repo.getWeather(lat, lon)) {
                is Handler.Success -> {
                    val entry = result.data!!.daily.mapIndexed{ _, entry ->
                        val day = getDayFromDate(entry.dt)
                        val icon = entry.weather[0].icon
                        val weatherType = entry.weather[0].main
                        val highestTemperature = getTemperature(entry.temp.max)
                        val lowestTemperature = getTemperature(entry.temp.min)
                        DailyWeather(day, icon, weatherType, highestTemperature, lowestTemperature)
                    }

                    dailyWeatherList.value = dailyWeatherList.value + entry
                    dailyWeatherList.value = dailyWeatherList.value.subList(1,8)
                    loadError.value = ""
                    isLoading.value = false
                }
                is Handler.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
            }
        }

    }

}