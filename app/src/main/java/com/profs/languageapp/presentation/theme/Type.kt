package com.profs.languageapp.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 1.sp,
        color = Dark,
        textAlign = TextAlign.Center
    ),
    bodyLarge = TextStyle(
        fontFamily = Fredoka,
        fontWeight = FontWeight.Medium,
        fontSize = 17.sp,
        lineHeight = 22.sp,
        letterSpacing = 1.sp,
        color = DefaultWhite,
        textAlign = TextAlign.Center
    ),
    bodyMedium = TextStyle(
        fontFamily = Fredoka,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 20.sp,
        letterSpacing = 1.sp,
        color = Dark,
        textAlign = TextAlign.Center
    ),

)