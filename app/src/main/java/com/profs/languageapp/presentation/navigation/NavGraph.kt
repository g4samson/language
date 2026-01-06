package com.profs.languageapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.profs.languageapp.data.utils.Destinations
import com.profs.languageapp.presentation.screens.splash.SplashScreen

@Composable
fun NavGraph(modifier: Modifier = Modifier, navController: NavHostController) {

    NavHost(navController = navController, startDestination = Destinations.Splash) {
        composable<Destinations.Splash> { SplashScreen(navController) }

        composable<Destinations.Onboarding> {  }

        composable<Destinations.Login> {  }

        composable<Destinations.Signup> {  }
    }

}