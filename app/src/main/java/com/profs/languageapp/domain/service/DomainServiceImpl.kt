package com.profs.languageapp.domain.service

import com.profs.languageapp.data.models.request.LoginUserRequest
import com.profs.languageapp.data.models.request.ModifyUserRatingRequest
import com.profs.languageapp.data.models.request.RegisterUserRequest
import com.profs.languageapp.data.models.response.CategoryResponse
import com.profs.languageapp.data.models.response.ComplexQuestionResponse
import com.profs.languageapp.data.models.response.LoginUserResponse
import com.profs.languageapp.data.models.response.RegisterUserResponse
import com.profs.languageapp.data.models.response.SimpleQuestionResponse
import com.profs.languageapp.data.models.response.UserRatingResponse
import com.profs.languageapp.data.utils.Constants
import com.profs.languageapp.data.utils.Provider
import javax.inject.Inject

class DomainServiceImpl @Inject constructor(
    private val localDataSource: Constants
) : DomainService {
    private val retrofit get() = Provider.provideRetrofit()

    override fun getAvailableLanguages() =
        localDataSource.getLanguages()

    override fun getOnboardingPages() =
        localDataSource.getOnboardingPages()


    override suspend fun registerUser(
        email: String,
        firstName: String,
        lastName: String,
        languageCode: String,
        password: String,
        image: String,
    ): RegisterUserResponse? {

        return try {
            val request = RegisterUserRequest(
                email = email,
                firstName = firstName,
                lastName = lastName,
                languageCode = languageCode,
                password = password,
                image = image,
            )
            retrofit.registerUser(request)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

    }

    override suspend fun loginUser(
        email: String,
        password: String
    ): LoginUserResponse? {
        return try {
            val request = LoginUserRequest(
                email = email,
                password = password,
            )
            retrofit.loginUser(request)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun modifyUserRating(userId: Long?, delta: Int) {
        val request = ModifyUserRatingRequest(
            userId = userId,
            delta = delta,
        )
        retrofit.modifyUserRating(request)
    }

    override suspend fun getUserRating(): List<UserRatingResponse>? {
        return try {
            retrofit.getUserRating()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun getAvailableCategories(): List<CategoryResponse>? {
        return try {
            retrofit.getAvailableCategories()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun getAllSimpleQuestions(): List<SimpleQuestionResponse>? {
        return try {
            retrofit.getAllSimpleQuestions()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun getAllComplexQuestions(): List<ComplexQuestionResponse>? {
        return try {
            retrofit.getAllComplexQuestions()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


}