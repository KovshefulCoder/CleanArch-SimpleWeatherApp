package com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.domain.models.ForecastDay
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.SharedFlow


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
internal fun ForecastView() {
    val viewModel: ForecastViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val flow = viewModel.event
    PrivateForecastView(
        forecasts = state.forecastItems,
        flow = flow,
        isRemoteLoading = state.isLoading
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PrivateForecastView(
    forecasts: List<ForecastDay>,
    flow: SharedFlow<Event>,
    isRemoteLoading: Boolean
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val isLoading by remember { mutableStateOf(isRemoteLoading) }
    LaunchedEffect(key1 = true) {
        flow.collect { event ->
            when(event) {
                is Event.ShowSnackbar -> {
                    snackBarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is Event.ShowLoadingIcon -> {
                    if (isLoading) {
                    }
                }
            }
        }
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        }
    ) {
        Box(modifier = Modifier
            .padding(it)
            .alpha(if (isLoading) 0.5f else 1f)
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyColumn() {
                    items(forecasts) {
                    }
                }
            }
        }
    }
}