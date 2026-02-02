package com.profs.languageapp.data.model.response

data class UserRatingResponse(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val rating: Int,
    val image: String
)
