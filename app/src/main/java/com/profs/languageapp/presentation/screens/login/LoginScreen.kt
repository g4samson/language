package com.profs.languageapp.presentation.screens.login

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.profs.languageapp.presentation.theme.Dark

@Composable
fun LoginScreen(
    navController: NavHostController,
    //viewModel: LoginViewModel
) {
    Text("LOGIN", color = Dark)
}