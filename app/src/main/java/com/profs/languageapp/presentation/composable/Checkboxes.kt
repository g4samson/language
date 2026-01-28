package com.profs.languageapp.presentation.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.profs.languageapp.R
import com.profs.languageapp.presentation.theme.Blue
import com.profs.languageapp.presentation.theme.DefaultWhite

@Composable
fun Checkbox(onValueChange: () -> Unit) {
    var checked by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .size(18.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(if (checked) Blue else DefaultWhite)
            .border(
                2.dp,
                Blue,
                RoundedCornerShape(4.dp)
            )
            .clickable {
                checked = !checked
                onValueChange()
            },
        contentAlignment = Alignment.Center
    ) {
        if (checked) {
            Icon(
                painter = painterResource(id = R.drawable.icon_checkbox),
                contentDescription = null,
                tint = DefaultWhite,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}