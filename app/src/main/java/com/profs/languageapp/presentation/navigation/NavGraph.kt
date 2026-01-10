package com.profs.languageapp.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.profs.languageapp.data.utils.Destinations
import com.profs.languageapp.presentation.screens.MainApplication
import com.profs.languageapp.presentation.screens.login.LoginScreen
import com.profs.languageapp.presentation.screens.onboarding.OnboardingScreen
import com.profs.languageapp.presentation.screens.onboarding.OnboardingViewModel
import com.profs.languageapp.presentation.screens.splash.SplashScreen

@Composable
fun NavGraph(modifier: Modifier = Modifier, navController: NavHostController) {

    NavHost(navController = navController, startDestination = Destinations.Splash) {

        composable<Destinations.Splash> {
            val viewModel: OnboardingViewModel = hiltViewModel()
            SplashScreen(navController, viewModel)
        }

        composable<Destinations.Onboarding> {
            val viewModel: OnboardingViewModel = hiltViewModel()
            OnboardingScreen(navController, viewModel)
        }

        composable<Destinations.Login> { LoginScreen(navController) }

        composable<Destinations.Signup> { }
    }

}