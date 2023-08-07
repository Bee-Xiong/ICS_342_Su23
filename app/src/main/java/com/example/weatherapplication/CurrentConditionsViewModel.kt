package com.example.weatherapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentConditionsViewModel @Inject constructor(private val apiService: Api) : ViewModel() {
    private val _weatherData: MutableLiveData<WeatherData> = MutableLiveData()
    val weatherData: LiveData<WeatherData>
        get() = _weatherData

    private var _currentZipCode: MutableLiveData<String> = MutableLiveData()

    private var zipCodeNumber = "55119"

    val zipCodeLiveData: MutableLiveData<String>
        get() = _currentZipCode

    val zipCode: String
        get() = zipCodeNumber

    fun setCurrentZipCode(zipCode: String) {
        _currentZipCode.value = zipCode
    }

    fun setZipCode(zipCode: String) {
        zipCodeNumber = zipCode
    }

    fun viewAppeared() = viewModelScope.launch {
        _weatherData.value = zipCode.toInt().let { apiService.getWeatherData(zip = it) }
    }

}