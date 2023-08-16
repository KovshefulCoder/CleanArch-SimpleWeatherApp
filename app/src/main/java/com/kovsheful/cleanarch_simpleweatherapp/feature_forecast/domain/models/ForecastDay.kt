package com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.domain.models

data class ForecastDay(
    val date: Pair<String, String>,
    val text: String,
    val icon: String,
    val avgtempC: Int,
    val maxwindKph: Int,
    val avgHumidity: Int
)
