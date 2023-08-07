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
    private val _forecastData: MutableLiveData<ForecastData> = MutableLiveData()
    val forecastData: LiveData<ForecastData>
        get() = _forecastData

    private var zipCodeNumber = 55119
    fun setZipCode(zipCode: String) {
        zipCodeNumber = zipCode.toInt()
        viewAppeared()
    }

    private fun viewAppeared() = viewModelScope.launch {
        _forecastData.value = apiService.getForecastData(zip = zipCodeNumber)
    }

}