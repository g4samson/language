package com.profs.languageapp.domain.service

import com.profs.languageapp.data.models.Excersise
import com.profs.languageapp.data.models.Language
import com.profs.languageapp.data.models.Page
import com.profs.languageapp.data.models.response.CategoryResponse
import com.profs.languageapp.data.models.response.UserRatingResponse
import com.profs.languageapp.data.models.response.LoginUserResponse
import com.profs.languageapp.data.models.response.RegisterUserResponse

interface DomainService {
    fun getAvailableLanguages(): List<Language>

    fun getExercises(): List<Excersise>

    fun getOnboardingPages(): List<Page>


    suspend fun registerUser(
        email: String,
        firstName: String,
        lastName: String,
        languageCode: String,
        password: String,
        image: String = "",
        rating: Int = 0
    ): RegisterUserResponse?

    suspend fun loginUser(email: String, password: String): LoginUserResponse?

    suspend fun getUserRating(): List<UserRatingResponse>?

    suspend fun getAvailableCategories(): List<CategoryResponse>?

}