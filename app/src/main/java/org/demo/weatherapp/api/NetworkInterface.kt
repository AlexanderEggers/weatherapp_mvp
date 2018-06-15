package org.demo.weatherapp.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkInterface {

    @GET("weather")
    fun getWeatherData(
            @Query("APPID") apiKey: String,
            @Query("q") cityCountry: String = "Melbourne,au",
            @Query("units") metric: String = "metric"): Call<String>
}