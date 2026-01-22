package com.profs.languageapp.data.utils

import kotlinx.serialization.Serializable
@Serializable
sealed class Destinations {

    @Serializable
    data object Splash : Destinations()

    @Serializable
    data object Onboarding : Destinations()

    @Serializable
    data object Login : Destinations()

    @Serializable
    data object Signup : Destinations()

    @Serializable
    data object Main : Destinations()

    @Serializable
    data object Profile : Destinations()

    @Serializable
    data object ProfileResizePhoto : Destinations()

    @Serializable
    data object LanguageSelect : Destinations()


    @Serializable
    data object ExcerciseAnimals : Destinations()

    @Serializable
    data object ExcerciseWordPractice : Destinations()

    @Serializable
    data object Game : Destinations()

    @Serializable
    data object ExcerciseListening : Destinations()
}