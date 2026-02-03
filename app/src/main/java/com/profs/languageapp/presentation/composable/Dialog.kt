package com.profs.languageapp.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ErrorDialog(
    message: String,
    onDismiss: () -> Unit
) {
    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Text(
                text = "OK",
                modifier = Modifier.clickable { onDismiss() }
            )
        },
        title = {
            Text("Ошибка")
        },
        text = {
            Text(message)
        }
    )
}