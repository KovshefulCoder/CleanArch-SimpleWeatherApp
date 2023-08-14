package com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.data.remote.dto

data class AirQuality(
    val co: Double,
    val gb-defra-index: Int,
    val no2: Double,
    val o3: Double,
    val pm10: Int,
    val pm2_5: Double,
    val so2: Int,
    val us-epa-index: Int
)