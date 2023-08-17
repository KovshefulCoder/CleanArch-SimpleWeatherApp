package com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.presentation

import com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.domain.models.ForecastDay


data class ForecastState (
    val forecastItems: List<ForecastDay> = emptyList(),
    val isLoading: Boolean = false
)