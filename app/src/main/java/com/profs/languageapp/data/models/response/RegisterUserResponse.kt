package com.profs.languageapp.data.models.response

data class RegisterUserResponse(
    val id: Long,
    val email: String,
    val firstName: String,
    val lastName: String,
    val languageCode: String,
    val rating: Int,
    val image: String
)