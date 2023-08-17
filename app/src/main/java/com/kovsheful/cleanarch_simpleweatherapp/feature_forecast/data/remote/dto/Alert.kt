package com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.data.remote.dto

// possible to replace Any to String?
data class Alert(
    val areas: Any,
    val category: String,
    val certainty: Any,
    val desc: String,
    val effective: String,
    val event: String,
    val expires: String,
    val headline: String,
    val instruction: String, //?
    val msgtype: Any,
    val note: Any,
    val severity: Any,
    val urgency: Any
)