package com.profs.languageapp.domain.di

import com.profs.languageapp.domain.usecase.OpenPdfUseCase
import com.profs.languageapp.domain.usecase.ValidateInputUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideValidateInputUseCase(): ValidateInputUseCase {
        return ValidateInputUseCase()
    }

    @Provides
    fun provideOpenPdfUseCase(): OpenPdfUseCase {
        return OpenPdfUseCase()
    }
}