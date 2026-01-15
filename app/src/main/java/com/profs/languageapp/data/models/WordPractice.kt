package com.profs.languageapp.data.models

data class WordPractice(
    val word: String,
    val trascription: String,
    val examples: List<VariantWordPractice>
)
