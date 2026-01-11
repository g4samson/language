package com.profs.languageapp.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.profs.languageapp.presentation.theme.Blue
import com.profs.languageapp.presentation.theme.DefaultWhite
import com.profs.languageapp.presentation.theme.Typography

@Composable
fun DefaultButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Blue, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp)),
        colors = ButtonDefaults.buttonColors(Blue)
    ) {
        Text(text, style = Typography.displayMedium.copy(color = DefaultWhite))
    }
}