package com.profs.languageapp.domain.service

import com.profs.languageapp.data.models.request.LoginUserRequest
import com.profs.languageapp.data.models.request.RegisterUserRequest
import com.profs.languageapp.data.models.response.CategoryResponse
import com.profs.languageapp.data.models.response.LoginUserResponse
import com.profs.languageapp.data.models.response.RegisterUserResponse
import com.profs.languageapp.data.models.response.UserRatingResponse
import com.profs.languageapp.data.source.LocalDataSource
import com.profs.languageapp.data.utils.Provider
import javax.inject.Inject

class DomainServiceImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : DomainService {
    private val retrofit get() = Provider.provideRetrofit()

    override fun getAvailableLanguages() =
        localDataSource.getLanguages()

    override fun getExercises() =
        localDataSource.getExercises()

    override fun getOnboardingPages() =
        localDataSource.getOnboardingPages()


    override suspend fun registerUser(
        email: String,
        firstName: String,
        lastName: String,
        languageCode: String,
        password: String,
        image: String,
        rating: Int
    ): RegisterUserResponse? {

        return try {
            val request = RegisterUserRequest(
                email = email,
                firstName = firstName,
                lastName = lastName,
                languageCode = languageCode,
                password = password,
                image = image,
                rating = rating
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


}