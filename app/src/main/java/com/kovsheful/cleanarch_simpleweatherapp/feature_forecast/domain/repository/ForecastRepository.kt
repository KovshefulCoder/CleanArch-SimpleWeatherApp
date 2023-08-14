package com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.domain.repository

import com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.core.util.Resource
import com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.domain.models.ForecastDay

interface ForecastRepository {

    fun getForecast(location: String, days: Int, apiKey: String) : Flow<Resource<List<ForecastDay>>>

//    fun getForecast(
//        text: String,
//        icon: String,
//        avgtempC: Double,
//        maxwindKph: Double,
//        avgHumidity: Int
//    ) : Flow<Resource<List<ForecastDay>>>
}