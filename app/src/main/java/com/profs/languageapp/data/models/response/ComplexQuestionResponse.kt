package com.profs.languageapp.data.models.response

data class ComplexQuestionResponse(
    val id: Long,
    val enName: String,
    val ruName: String,
    val enTranscription: String,
    val ruTranscription: String
)
