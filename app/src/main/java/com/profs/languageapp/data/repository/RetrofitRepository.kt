package com.profs.languageapp.data.repository

import com.profs.languageapp.data.model.request.LoginUserRequest
import com.profs.languageapp.data.model.request.ModifyUserRatingRequest
import com.profs.languageapp.data.model.request.RegisterUserRequest
import com.profs.languageapp.data.model.response.CategoryResponse
import com.profs.languageapp.data.model.response.ComplexQuestionResponse
import com.profs.languageapp.data.model.response.LoginUserResponse
import com.profs.languageapp.data.model.response.RegisterUserResponse
import com.profs.languageapp.data.model.response.SimpleQuestionResponse
import com.profs.languageapp.data.model.response.UserRatingResponse
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