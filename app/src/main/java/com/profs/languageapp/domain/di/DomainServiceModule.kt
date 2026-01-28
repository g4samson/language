package com.profs.languageapp.domain.di

import com.profs.languageapp.domain.service.DomainService
import com.profs.languageapp.domain.service.DomainServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainServiceModule {

    @Binds
    @Singleton
    abstract fun bindLearningDomainService(
        impl: DomainServiceImpl
    ): DomainService
}