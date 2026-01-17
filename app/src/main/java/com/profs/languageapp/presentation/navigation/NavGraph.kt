package com.profs.languageapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.profs.languageapp.data.utils.Destinations
import com.profs.languageapp.presentation.screens.languageSelect.LanguageSelectScreen
import com.profs.languageapp.presentation.screens.languageSelect.LanguageSelectViewModel
import com.profs.languageapp.presentation.screens.login.LoginScreen
import com.profs.languageapp.presentation.screens.login.LoginViewModel
import com.profs.languageapp.presentation.screens.main.MainScreen
import com.profs.languageapp.presentation.screens.main.MainViewModel
import com.profs.languageapp.presentation.screens.onboarding.OnboardingScreen
import com.profs.languageapp.presentation.screens.onboarding.OnboardingViewModel
import com.profs.languageapp.presentation.screens.profile.ProfileScreen
import com.profs.languageapp.presentation.screens.profile.ThemeViewModel
import com.profs.languageapp.presentation.screens.signup.SignupScreen
import com.profs.languageapp.presentation.screens.signup.SignupViewModel
import com.profs.languageapp.presentation.screens.splash.SplashScreen

@Composable
fun NavGraph(modifier: Modifier = Modifier, navController: NavHostController, theme: ThemeViewModel) {

    NavHost(navController = navController, startDestination = Destinations.Splash) {

        composable<Destinations.Splash> {
            val viewModel: OnboardingViewModel = hiltViewModel()
            SplashScreen(navController, viewModel)
        }

        composable<Destinations.Onboarding> {
            val viewModel: OnboardingViewModel = hiltViewModel()
            OnboardingScreen(navController, viewModel)
        }

        composable<Destinations.Login> {
            val viewModel: LoginViewModel = hiltViewModel()
            LoginScreen(navController, viewModel)
        }

        composable<Destinations.Signup> {
            val viewModel: SignupViewModel = hiltViewModel()
            SignupScreen(navController, viewModel)
        }

        composable<Destinations.Main> {
            val viewModel: MainViewModel = hiltViewModel()
            MainScreen(navController, viewModel)
        }

        composable<Destinations.Profile> {
            ProfileScreen(navController, theme)
        }

        composable<Destinations.LanguageSelect> {
            val viewModel: LanguageSelectViewModel = hiltViewModel()
            LanguageSelectScreen(navController, viewModel)
        }
    }

}