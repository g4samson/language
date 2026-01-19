package com.profs.languageapp.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = Dark,
    secondary = DarkLighter,
    tertiary = Blue,
    surface = DefaultWhite,
    background = DefaultWhite
)

private val DarkColorScheme = darkColorScheme(
    primary = DefaultWhite,
    secondary = DefaultWhite.copy(0.6f),
    tertiary = Blue,
    surface = Dark,
    background = Dark
)

@Composable
fun LanguageAppTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}