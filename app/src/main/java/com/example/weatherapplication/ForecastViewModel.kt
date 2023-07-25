package com.example.weatherapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(private val apiService: Api) : ViewModel() {

    private val _weatherData: MutableLiveData<ForecastData> = MutableLiveData()
    val weatherData: LiveData<ForecastData>
        get() = _weatherData

    fun viewAppeared() = viewModelScope.launch {
        _weatherData.value = apiService.getForecastData()
    }

}