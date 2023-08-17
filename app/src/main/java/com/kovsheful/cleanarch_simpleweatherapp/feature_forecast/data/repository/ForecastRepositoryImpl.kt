package com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.data.repository

import android.util.Log
import com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.core.util.Resource
import com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.data.remote.ForecastAPI
import com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.domain.models.ForecastDay
import com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.domain.repository.ForecastRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class ForecastRepositoryImpl(
    private val api: ForecastAPI
) : ForecastRepository {

    override fun getForecast(
        location: String,
        days: Int,
        apiKey: String
    ): Flow<Resource<List<ForecastDay>>> = flow {
        emit(Resource.Loading())
        try {
            val forecast = api.getWeatherForecast(
                location = location,
                days = days,
                apiKey = apiKey
            ).forecast.forecastday.map {
                it.day.toForecastDay(date = it.date)
            }
            emit(Resource.Success(data = forecast))
        } catch (e: HttpException) {
            emit(Resource.Error(e.code().toString()))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn`t reach server!"))
        }
    }
}