package com.profs.languageapp.presentation.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.profs.languageapp.R

@Composable
fun NoInternetScreen(onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.logo_main),
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Нет соединения с интернетом")

        Spacer(modifier = Modifier.height(24.dp))

        DefaultButton("Повторить", onClick = onRetry)
    }
}