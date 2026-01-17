package com.profs.languageapp.presentation.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.profs.languageapp.data.utils.Constants
import com.profs.languageapp.presentation.navigation.NavGraph
import com.profs.languageapp.presentation.screens.profile.ThemeViewModel
import com.profs.languageapp.presentation.theme.LanguageAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val themeViewModel: ThemeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        runBlocking {
            Constants.getLanguage(this@MainActivity)
                .firstOrNull()?.let {
                    Constants.setLanguage(it)
                }
        }


        setContent {
            val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

            LanguageAppTheme(darkTheme = isDarkTheme) {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavGraph(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        theme = themeViewModel
                    )
                }
            }
        }
    }
}