package com.profs.languageapp.data.utils

import com.profs.languageapp.R
import com.profs.languageapp.data.models.Excersise
import com.profs.languageapp.data.models.Language
import com.profs.languageapp.presentation.theme.Blue
import com.profs.languageapp.presentation.theme.Green
import com.profs.languageapp.presentation.theme.Orange
import com.profs.languageapp.presentation.theme.Red

object Constants {
    val languages = listOf<Language>(
        Language("Russian"),
        Language("English"),
        Language("Chinese"),
        Language("Belarus"),
        Language("Kazakh"),
    )

    val excersises = listOf<Excersise>(
        Excersise("Guess the animal", R.drawable.ex_bear, Blue),
        Excersise("Word practice", R.drawable.ex_pen, Red),
        Excersise("Audition", R.drawable.ex_audio, Orange),
        Excersise("Game", R.drawable.ex_game, Green),
    )

}