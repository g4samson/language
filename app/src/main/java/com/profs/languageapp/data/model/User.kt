package com.profs.languageapp.data.model

data class User(
    val id: Long,
    val email: String,
    val firstName: String,
    val lastName: String,
    val languageCode: String,
    val rating: Int,
    val image: String
)
