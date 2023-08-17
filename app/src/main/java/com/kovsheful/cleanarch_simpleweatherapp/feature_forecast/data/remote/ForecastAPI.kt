package com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.data.remote

import com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.data.remote.dto.ForecastDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastAPI {
    @GET("forecast.json")
    suspend fun getWeatherForecast(
        @Query("q") location: String,
        @Query("days") days: Int,
        @Query("key") apiKey: String
    ): ForecastDto

    companion object {
        const val BASE_API = "https://api.weatherapi.com/v1/"
    }
}

