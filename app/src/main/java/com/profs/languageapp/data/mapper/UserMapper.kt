package com.profs.languageapp.data.mapper

import com.profs.languageapp.data.models.response.LoginUserResponse
import com.profs.languageapp.data.models.response.UserRatingResponse
import com.profs.languageapp.domain.model.User

// LoginUserResponse → User
fun LoginUserResponse.toUser(): User {
    return User(
        id = this.id,
        email = this.email,
        firstName = this.firstName,
        lastName = this.lastName,
        languageCode = this.languageCode,
        rating = this.rating,
        image = this.image
    )
}

// UserRatingResponse → User
fun UserRatingResponse.toUser(defaultEmail: String = "", defaultLanguageCode: String = "en"): User {
    return User(
        id = this.id,
        email = defaultEmail,
        firstName = this.firstName,
        lastName = this.lastName,
        languageCode = defaultLanguageCode,
        rating = this.rating,
        image = this.image
    )
}

// User → UserRatingResponse
fun User.toUserRatingResponse(): UserRatingResponse {
    return UserRatingResponse(
        id = this.id,
        firstName = this.firstName,
        lastName = this.lastName,
        rating = this.rating,
        image = this.image
    )
}