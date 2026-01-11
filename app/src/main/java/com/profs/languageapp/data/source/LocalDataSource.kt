package com.profs.languageapp.data.source

import com.profs.languageapp.R
import com.profs.languageapp.data.models.Excersise
import com.profs.languageapp.data.models.Language
import com.profs.languageapp.data.models.Page
import com.profs.languageapp.data.models.User
import com.profs.languageapp.presentation.theme.Blue
import com.profs.languageapp.presentation.theme.Green
import com.profs.languageapp.presentation.theme.Orange
import com.profs.languageapp.presentation.theme.Red
import javax.inject.Inject

class LocalDataSource @Inject constructor() {

    fun getLanguages() = listOf(
        Language("Russian"),
        Language("English"),
        Language("Chinese"),
        Language("Belarus"),
        Language("Kazakh"),
    )

    fun getExercises() = listOf(
        Excersise("Guess the animal", R.drawable.ex_bear, Blue),
        Excersise("Word practice", R.drawable.ex_pen, Red),
        Excersise("Audition", R.drawable.ex_audio, Orange),
        Excersise("Game", R.drawable.ex_game, Green),
    )

    fun getOnboardingPages() = listOf(
        Page(
            R.drawable.onboarding_1,
            R.drawable.slider_1,
            "Confidence in your words",
            "With conversation-based learning, you'll be talking from lesson one",
            "Next"
        ),
        Page(
            R.drawable.onboarding_2,
            R.drawable.slider_2,
            "Take your time to learn",
            "Develop a habit of learning and make it a part of your daily routine",
            "More"
        ),
        Page(
            R.drawable.onboarding_3,
            R.drawable.slider_3,
            "The lessons you need to learn",
            "Using a variety of learning styles to learn and retain",
            "Choose a language"
        )
    )

    fun getTopUsers() = listOf(
        User("Vincent van Gogh", R.drawable.user_1, 12),
        User("Dmitri Ivanovich Mendeleev", R.drawable.user_2, 10),
        User("Vlad Tepes", R.drawable.user_3, 8),
    )
}