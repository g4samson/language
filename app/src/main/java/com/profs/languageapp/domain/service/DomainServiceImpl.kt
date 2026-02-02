package com.profs.languageapp.domain.service

import com.profs.languageapp.data.model.request.LoginUserRequest
import com.profs.languageapp.data.model.request.ModifyUserRatingRequest
import com.profs.languageapp.data.model.request.RegisterUserRequest
import com.profs.languageapp.data.model.response.CategoryResponse
import com.profs.languageapp.data.model.response.ComplexQuestionResponse
import com.profs.languageapp.data.model.response.LoginUserResponse
import com.profs.languageapp.data.model.response.RegisterUserResponse
import com.profs.languageapp.data.model.response.SimpleQuestionResponse
import com.profs.languageapp.data.model.response.UserRatingResponse
import com.profs.languageapp.data.utils.Provider
import com.profs.languageapp.data.utils.Storage
import javax.inject.Inject

class DomainServiceImpl @Inject constructor(
    private val storage: Storage
) : DomainService {
    private val retrofit get() = Provider.provideRetrofit()

    override fun getAvailableLanguages() =
        storage.getLanguages()

    override fun getOnboardingPages() =
        storage.getOnboardingPages()


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