package com.profs.languageapp.data.models

import androidx.compose.ui.graphics.Color
import com.profs.languageapp.data.utils.Destinations

data class Excersise(
    val name: String,
    val image: Int,
    val color: Color,
    val dest: Destinations
)
