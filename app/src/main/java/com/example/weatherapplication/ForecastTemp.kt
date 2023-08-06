package com.example.weatherapplication

import com.squareup.moshi.Json

data class ForecastTemp(
    @Json(name = "day") val day: Float,
    @Json(name = "min") val min: Float,
    @Json(name = "max") val max: Float,
)

