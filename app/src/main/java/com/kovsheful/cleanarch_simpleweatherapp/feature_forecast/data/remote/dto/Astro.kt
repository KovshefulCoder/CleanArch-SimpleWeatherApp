package com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.data.remote.dto

data class Astro(
    val moon_illumination: String,
    val moon_phase: String,
    val moonrise: String,
    val moonset: String,
    val sunrise: String,
    val sunset: String
)