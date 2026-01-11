package com.profs.languageapp.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.profs.languageapp.data.models.Language
import com.profs.languageapp.presentation.theme.Orange
import com.profs.languageapp.presentation.theme.Typography

@Composable
fun LanguageCard(language: Language) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(67.dp)
            .background(Orange.copy(alpha = 0.1f), RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .clickable {

            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(15.dp))
        Text(language.name, style = Typography.titleLarge)
    }
}