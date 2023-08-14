package com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.domain.models

data class ForecastDay(
    val text: String,
    val icon: String,
    val avgtempC: Double,
    val maxwindKph: Double,
    val avgHumidity: Int
)
