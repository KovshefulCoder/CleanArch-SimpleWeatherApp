package com.kovsheful.cleanarch_simpleweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.presentation.forecastScreen
import com.kovsheful.cleanarch_simpleweatherapp.ui.theme.Background
import com.kovsheful.cleanarch_simpleweatherapp.ui.theme.CleanArch_SimpleWeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CleanArch_SimpleWeatherAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Background
                ) {
                    SimpleWeatherApp()
                }
            }
        }
    }
}

@Composable
fun SimpleWeatherApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "ForecastScreen"
    ) {
        navigation(
            route = "ForecastScreen",
            startDestination = "ForecastList"
        ) {
            forecastScreen()
        }
    }
}