package com.example.weatherapplication


import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET(value = "weather")
    suspend fun getWeatherData(
        @Query("zip") zip: Int = 55119,
        @Query("units") units: String = "imperial",
        @Query("appid") appid: String = "6eb4cee544daa94d85abcc61622773d1",
    ): WeatherData

    @GET(value = "forecast/daily")
    suspend fun getForecastData(
        @Query("zip") zip: Int = 55119,
        @Query("units") units: String = "imperial",
        @Query("cnt") cnt: Int = 16,
        @Query("appid") appid: String = "6eb4cee544daa94d85abcc61622773d1",
    ): ForecastData

}