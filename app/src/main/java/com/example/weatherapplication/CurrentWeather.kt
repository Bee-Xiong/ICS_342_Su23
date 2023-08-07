package com.example.weatherapplication

import com.squareup.moshi.Json


data class CurrentWeather(
    @Json(name = "temp") val temp: Float,
    @Json(name = "feels_like") val feelsLike: Float,
    @Json(name = "temp_min") val tempMin: Float,
    @Json(name = "temp_max") val tempMax: Float,
    @Json(name = "pressure") val pressure: Int,
    @Json(name = "humidity") val humidity: Int,
)

data class Country(
    @Json(name = "country") val country: String,
)

data class WeatherData(
    @Json(name = "main") val main: CurrentWeather,
    @Json(name = "weather") val weather: List<Icon>,
    @Json(name = "sys") val country: Country,
    @Json(name = "name") val name: String,
) {
    val iconUrl: String
        get() = "https://openweathermap.org/img/wn/${weather.firstOrNull()?.icon}@2x.png"

}