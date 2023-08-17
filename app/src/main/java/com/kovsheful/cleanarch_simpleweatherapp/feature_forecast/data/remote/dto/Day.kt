package com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.data.remote.dto

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.domain.models.ForecastDay
import java.time.LocalDateTime
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

data class Day(
    val avghumidity: Int,
    val avgtemp_c: Double,
    val avgtemp_f: Double,
    val avgvis_km: Double,
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
    val totalprecip_in: Double,
    val totalprecip_mm: Double,
    val uv: Int
) {
    fun toForecastDay(date: String) : ForecastDay {
        val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
        val dateTime = LocalDate.parse(date, inputFormat)
        val outputFormat = DateTimeFormatter.ofPattern("EEEE, dd MMMM", Locale.ENGLISH)
        val weekDayWithData = dateTime.format(outputFormat).split(",")
        val datePair: Pair<String, String> = Pair(weekDayWithData[0], weekDayWithData[1])
        return ForecastDay(
            date = datePair,
            text = condition.text,
            icon = condition.icon,
            avgtempC = avgtemp_c.toInt(),
            maxwindKph = maxwind_kph.toInt(),
            avgHumidity = avghumidity
        )
    }
}