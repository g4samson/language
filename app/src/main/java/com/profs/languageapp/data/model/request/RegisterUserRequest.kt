package com.profs.languageapp.data.model.request

data class RegisterUserRequest(
    val email: String,
    val firstName: String,
    val lastName: String,
    val languageCode: String,
    val image: String,
    val password: String
)