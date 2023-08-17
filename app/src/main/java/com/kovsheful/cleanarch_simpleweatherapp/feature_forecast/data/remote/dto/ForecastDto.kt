package com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.data.remote.dto

data class ForecastDto(
    val alerts: Alerts,
    val current: Current,
    val forecast: Forecast,
    val location: Location
)