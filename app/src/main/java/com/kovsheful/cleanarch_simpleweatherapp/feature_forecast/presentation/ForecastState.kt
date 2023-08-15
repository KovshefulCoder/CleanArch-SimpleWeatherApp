package com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.domain.models.ForecastDay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow


data class ForecastState (
    val forecastItems: List<ForecastDay> = emptyList(),
    val isLoading: Boolean = false
) {
    private val _state = mutableStateOf(ForecastState())
    val state: State<ForecastState> = _state

    private val _eventFlow = MutableSharedFlow<Event>()
    val event = _eventFlow.asSharedFlow()

    fun getForecast() {
        val location: String = "moscow"
        val nDaysForForecast: Int = 5

    }

}

sealed class Event {
    data class ShowSnackbar(val message: String): Event()
}