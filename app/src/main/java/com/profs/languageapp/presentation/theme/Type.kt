package com.profs.languageapp.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.profs.languageapp.R


val Fredoka = FontFamily(
    Font(R.font.fredoka_bold, FontWeight.Bold),
    Font(R.font.fredoka_semibold, FontWeight.SemiBold),
    Font(R.font.fredoka_medium, FontWeight.Medium),
    Font(R.font.fredoka_regular, FontWeight.Normal),
    Font(R.font.fredoka_light, FontWeight.Light),
    )
val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = Fredoka,
        fontWeight = FontWeight.SemiBold,
        fontSize = 36.sp,
        lineHeight = 42.sp,
        letterSpacing = 0.sp,
        color = DefaultWhite
    ),
    bodyLarge = TextStyle(
        fontFamily = Fredoka,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = 1.sp,
        color = DefaultWhite
    ),

)