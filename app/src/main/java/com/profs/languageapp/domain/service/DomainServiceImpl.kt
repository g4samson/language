package com.profs.languageapp.domain.service

import com.profs.languageapp.data.source.LocalDataSource
import javax.inject.Inject

class DomainServiceImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : DomainService {

    override fun getAvailableLanguages() =
        localDataSource.getLanguages()

    override fun getExercises() =
        localDataSource.getExercises()

    override fun getOnboardingPages() =
        localDataSource.getOnboardingPages()
}