package com.profs.languageapp.data.utils

import kotlinx.serialization.Serializable
@Serializable
sealed class Destinations {

    @Serializable
    object Splash : Destinations()

    @Serializable
    object Onboarding : Destinations()

    @Serializable
    object Login : Destinations()

    @Serializable
    object Signup : Destinations()
}