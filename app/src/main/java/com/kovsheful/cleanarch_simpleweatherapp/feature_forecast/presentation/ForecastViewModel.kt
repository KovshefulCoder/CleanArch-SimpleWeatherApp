package com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kovsheful.cleanarch_simpleweatherapp.BuildConfig
import com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.core.util.Resource
import com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.domain.use_cases.GetForecast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor (
    private val getForecast: GetForecast
) : ViewModel()  {
    private val _state = MutableStateFlow(ForecastState())
    val state: StateFlow<ForecastState> = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<Event>()
    val event = _eventFlow.asSharedFlow()

    init {
        Log.i("ForecastViewModel", "init getRemoteForecast()")
        getRemoteForecast()
    }
    fun getRemoteForecast() {
        val location: String = "moscow"
        val nDaysForForecast: Int = 5
        val apiKey: String = "bbeeb5bff806437b98f134134231408"
        viewModelScope.launch {
            getForecast(location, nDaysForForecast, apiKey)
                .collect { result ->
                    Log.i("ForecastViewModel", "RESULT getRemoteForecast: ${result.data}")
                    when(result) {
                        is Resource.Success -> {
                            _state.value = state.value.copy(
                                forecastItems = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }

                        is Resource.Error -> {
                            _state.value = state.value.copy(
                                forecastItems = result.data ?: emptyList(),
                                isLoading = false
                            )
                            _eventFlow.emit(Event.ShowSnackbar(result.message ?: "Unknown error"))
                        }

                        is Resource.Loading -> {
                            _state.value = state.value.copy(
                                forecastItems = result.data ?: emptyList(),
                                isLoading = true
                            )
                        }
                    }
                }
        }
    }
}

sealed class Event {
    data class ShowSnackbar(val message: String): Event()
}