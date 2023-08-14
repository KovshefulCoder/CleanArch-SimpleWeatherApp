package com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.data.remote

import com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.data.remote.dto.ForecastDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastAPI {
    @GET("forecast.json")
    fun getWeatherForecast(
        @Query("q") location: String,
        @Query("days") days: Int,
        @Query("key") apiKey: String
    ): ForecastDto
}

