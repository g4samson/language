package com.profs.languageapp.presentation.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.profs.languageapp.R
import com.profs.languageapp.data.utils.Destinations
import com.profs.languageapp.presentation.composable.DefaultButton
import com.profs.languageapp.presentation.theme.DeepBlue
import com.profs.languageapp.presentation.theme.DefaultWhite
import com.profs.languageapp.presentation.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavHostController,
    themeViewModel: ThemeViewModel
) {
    val colors = MaterialTheme.colorScheme
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

    val switchThemeText = if (isDarkTheme) {
        stringResource(R.string.switch_to_light)
    } else {
        stringResource(R.string.switch_to_dark)

    }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Image(
                        painterResource(R.drawable.user_0),
                        contentDescription = null,
                        modifier = Modifier.size(134.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        "Your profile, Emil",
                        style = Typography.titleLarge.copy(color = DefaultWhite),
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }, modifier = Modifier
                .fillMaxWidth()
                .height(231.dp),
            colors = TopAppBarDefaults.topAppBarColors(containerColor = DeepBlue)
        )
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(bottom = 26.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DefaultButton(switchThemeText) { themeViewModel.toggleTheme() }
                Spacer(modifier = Modifier.height(10.dp))
                DefaultButton("Change mother language") { navController.navigate(Destinations.LanguageSelect) }
                Spacer(modifier = Modifier.height(10.dp))
                DefaultButton("Change your image") { }
                Spacer(modifier = Modifier.height(10.dp))
                DefaultButton("Logout") { navController.navigate(Destinations.Login) }
            }
        }
    }
}