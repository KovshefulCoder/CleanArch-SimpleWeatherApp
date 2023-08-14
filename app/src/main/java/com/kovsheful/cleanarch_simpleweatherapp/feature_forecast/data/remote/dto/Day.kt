package com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.data.remote.dto

import com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.domain.models.ForecastDay

data class Day(
    val avghumidity: Int,
    val avgtemp_c: Double,
    val avgtemp_f: Double,
    val avgvis_km: Int,
    val avgvis_miles: Int,
    val condition: Condition,
    val daily_chance_of_rain: Int,
    val daily_chance_of_snow: Int,
    val daily_will_it_rain: Int,
    val daily_will_it_snow: Int,
    val maxtemp_c: Double,
    val maxtemp_f: Double,
    val maxwind_kph: Double,
    val maxwind_mph: Double,
    val mintemp_c: Double,
    val mintemp_f: Double,
    val totalprecip_in: Int,
    val totalprecip_mm: Int,
    val uv: Int
) {
    fun toForecast() : ForecastDay {
        return ForecastDay(
            text = condition.text,
            icon = condition.icon,
            avgtempC = avgtemp_c,
            maxwindKph = maxwind_kph,
            avgHumidity = avghumidity
        )
    }
}