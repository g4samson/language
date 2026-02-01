package com.profs.languageapp.data.repository

import com.profs.languageapp.data.models.request.LoginUserRequest
import com.profs.languageapp.data.models.request.ModifyUserRatingRequest
import com.profs.languageapp.data.models.request.RegisterUserRequest
import com.profs.languageapp.data.models.response.CategoryResponse
import com.profs.languageapp.data.models.response.ComplexQuestionResponse
import com.profs.languageapp.data.models.response.UserRatingResponse
import com.profs.languageapp.data.models.response.LoginUserResponse
import com.profs.languageapp.data.models.response.RegisterUserResponse
import com.profs.languageapp.data.models.response.SimpleQuestionResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface RetrofitRepository {

    @POST("user/register")
    suspend fun registerUser(
        @Body request: RegisterUserRequest
    ): RegisterUserResponse

    @POST("user/login")
    suspend fun loginUser(
        @Body request: LoginUserRequest
    ): LoginUserResponse

    @PATCH("user")
    suspend fun modifyUserRating(@Body request: ModifyUserRatingRequest)

    @GET("user/rating")
    suspend fun getUserRating() :  List<UserRatingResponse>

    @GET("category/getAvailableCategories")
    suspend fun getAvailableCategories() :  List<CategoryResponse>

    @GET("question/getAllSimpleQuestions")
    suspend fun getAllSimpleQuestions() :  List<SimpleQuestionResponse>

    @GET("question/getAllComplexQuestions")
    suspend fun getAllComplexQuestions() :  List<ComplexQuestionResponse>
}