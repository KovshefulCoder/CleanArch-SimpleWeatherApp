package com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AirQuality(
    val co: Double,
    @SerializedName("gb-defra-index")
    val gbDefraIndex: Int,
    val no2: Double,
    val o3: Double,
    val pm10: Int,
    val pm2_5: Double,
    val so2: Int,
    @SerializedName("us-epa-index")
    val usEpaIndex: Int
)