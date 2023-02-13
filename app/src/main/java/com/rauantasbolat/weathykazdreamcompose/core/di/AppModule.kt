package com.rauantasbolat.weathykazdreamcompose.core.di

import com.rauantasbolat.weathykazdreamcompose.core.constants.Constants.BASE_URL
import com.rauantasbolat.weathykazdreamcompose.network.Api
import com.rauantasbolat.weathykazdreamcompose.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRepo(
        api: Api
    ) = WeatherRepository(api)

    @Singleton
    @Provides
    fun provideRetrofit(): Api {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(Api::class.java)
    }
}