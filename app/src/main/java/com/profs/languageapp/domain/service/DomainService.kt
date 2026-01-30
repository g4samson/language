package com.profs.languageapp.domain.service

import com.profs.languageapp.data.models.Language
import com.profs.languageapp.data.models.Page
import com.profs.languageapp.data.models.response.CategoryResponse
import com.profs.languageapp.data.models.response.ComplexQuestionResponse
import com.profs.languageapp.data.models.response.LoginUserResponse
import com.profs.languageapp.data.models.response.RegisterUserResponse
import com.profs.languageapp.data.models.response.SimpleQuestionResponse
import com.profs.languageapp.data.models.response.UserRatingResponse

interface DomainService {
    fun getAvailableLanguages(): List<Language>

    fun getOnboardingPages(): List<Page>

    suspend fun registerUser(
        email: String,
        firstName: String,
        lastName: String,
        languageCode: String,
        password: String,
        image: String = "",
    ): RegisterUserResponse?

    suspend fun loginUser(email: String, password: String): LoginUserResponse?

    suspend fun getUserRating(): List<UserRatingResponse>?

    suspend fun getAvailableCategories(): List<CategoryResponse>?

    suspend fun getAllSimpleQuestions(): List<SimpleQuestionResponse>?

    suspend fun getAllComplexQuestions(): List<ComplexQuestionResponse>?

}