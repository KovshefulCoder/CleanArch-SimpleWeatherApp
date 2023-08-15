package com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.presentation


data class ForecastState (
    val forecastItems: List<ForecastDay> = emptyList(),
    val isLoading: Boolean = false
)