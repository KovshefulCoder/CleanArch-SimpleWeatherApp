package com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.di

import com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.data.remote.ForecastAPI
import com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.data.repository.ForecastRepositoryImpl
import com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.domain.repository.ForecastRepository
import com.kovsheful.cleanarch_simpleweatherapp.feature_forecast.domain.use_cases.GetForecast
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ForecastModule {

    @Provides
    @Singleton
    fun provideGetForecastUseCase(repository: ForecastRepository): GetForecast {
        return GetForecast(repository = repository)
    }

    @Provides
    @Singleton
    fun provideForecastRepository(
        api: ForecastAPI
    ): ForecastRepository {
        return ForecastRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideForecastApi(): ForecastAPI {
        return Retrofit.Builder()
            .baseUrl(ForecastAPI.BASE_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ForecastAPI::class.java)
    }
}
