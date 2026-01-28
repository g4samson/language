package com.profs.languageapp.data.utils

import com.profs.languageapp.data.repository.RetrofitRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Provider {
    @Provides
    @Singleton
    fun provideRetrofit(): RetrofitRepository {
        return Retrofit.Builder().baseUrl("http://10.0.2.2:8888/api/")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(RetrofitRepository::class.java)
    }
}