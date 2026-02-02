package com.profs.languageapp.data.model.request

data class ModifyUserRatingRequest(
    val userId: Long?,
    val delta: Int
)
