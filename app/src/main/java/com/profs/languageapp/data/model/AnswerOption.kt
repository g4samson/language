package com.profs.languageapp.data.model

data class AnswerOption(
    val text: String,
    val isCorrect: Boolean,
    val isSelected: Boolean = false
)