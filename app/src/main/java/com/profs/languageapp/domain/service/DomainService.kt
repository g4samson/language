package com.profs.languageapp.domain.service

import com.profs.languageapp.data.models.Excersise
import com.profs.languageapp.data.models.Language
import com.profs.languageapp.data.models.Page
import com.profs.languageapp.data.models.User

interface DomainService {
    fun getAvailableLanguages(): List<Language>

    fun getExercises(): List<Excersise>

    fun getOnboardingPages(): List<Page>

    fun getTopUsers(): List<User>

}