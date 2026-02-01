package com.profs.languageapp.data.models.request

data class ModifyUserRatingRequest(
    val userId: Long?,
    val delta: Int
)
