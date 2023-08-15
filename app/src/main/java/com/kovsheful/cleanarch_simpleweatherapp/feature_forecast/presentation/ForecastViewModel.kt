package com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.presentation

import com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.domain.use_cases.GetForecast
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class ForecastViewModel @Inject constructor (
    private val getForecast: GetForecast
) : ViewModel() {

}