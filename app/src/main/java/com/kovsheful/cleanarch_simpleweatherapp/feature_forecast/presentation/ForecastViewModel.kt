package com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.domain.use_cases.GetForecast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor (
    private val getForecast: GetForecast
) : ViewModel()  {
    private val _state = mutableStateOf(ForecastState())
    val state: State<ForecastState> = _state

    private val _eventFlow = MutableSharedFlow<Event>()
    val event = _eventFlow.asSharedFlow()

    fun getForecast() {
        val location: String = "moscow"
        val nDaysForForecast: Int = 5
        val apiKey: String = BuildConfig.API_KEY

    }

}

sealed class Event {
    data class ShowSnackbar(val message: String): Event()
}