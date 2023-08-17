package com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.domain.use_cases

import com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.core.util.Resource
import com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.domain.models.ForecastDay
import com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.domain.repository.ForecastRepository
import kotlinx.coroutines.flow.Flow

class GetForecast(
    private val repository: ForecastRepository
) {
    operator fun invoke (location: String, days: Int, apiKey: String) :  Flow<Resource<List<ForecastDay>>> {
        return repository.getForecast(location, days, apiKey)
    }
}