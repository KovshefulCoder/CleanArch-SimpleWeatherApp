package com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.domain.use_cases

import com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.domain.repository.ForecastRepository

class GetForecast(
    private val repository: ForecastRepository
) {
    operator fun invoke(location: String, days: Int, apiKey: String) {
        return repository.getForecast(location, days, apiKey)
    }
}