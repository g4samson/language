package com.profs.languageapp.domain.service

import com.profs.languageapp.data.model.Language
import com.profs.languageapp.data.model.Page
import com.profs.languageapp.data.model.response.CategoryResponse
import com.profs.languageapp.data.model.response.ComplexQuestionResponse
import com.profs.languageapp.data.model.response.LoginUserResponse
import com.profs.languageapp.data.model.response.RegisterUserResponse
import com.profs.languageapp.data.model.response.SimpleQuestionResponse
import com.profs.languageapp.data.model.response.UserRatingResponse

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

    suspend fun modifyUserRating(userId: Long?, delta: Int)

    suspend fun getUserRating(): List<UserRatingResponse>?

    suspend fun getAvailableCategories(): List<CategoryResponse>?

    suspend fun getAllSimpleQuestions(): List<SimpleQuestionResponse>?

    suspend fun getAllComplexQuestions(): List<ComplexQuestionResponse>?

}