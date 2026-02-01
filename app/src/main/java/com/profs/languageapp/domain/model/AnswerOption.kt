package com.profs.languageapp.domain.model

data class AnswerOption(
    val text: String,
    val isCorrect: Boolean,
    val isSelected: Boolean = false
)