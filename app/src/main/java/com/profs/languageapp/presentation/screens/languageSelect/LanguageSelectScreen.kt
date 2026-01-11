package com.profs.languageapp.presentation.screens.languageSelect

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.profs.languageapp.R
import com.profs.languageapp.presentation.composable.DefaultButton
import com.profs.languageapp.presentation.composable.LanguageCard
import com.profs.languageapp.presentation.theme.DeepBlue
import com.profs.languageapp.presentation.theme.DefaultWhite
import com.profs.languageapp.presentation.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageSelectScreen(
    navController: NavHostController,
    viewModel: LanguageSelectViewModel
) {
    val languageList = viewModel.languageList.collectAsState(initial = listOf()).value

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Language select",
                        style = Typography.bodyLarge,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(102.dp),
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
            Spacer(modifier = Modifier.height(12.dp))

            Text("What is your Mother language?", style = Typography.titleLarge)

            Spacer(modifier = Modifier.height(16.dp))

            if (!languageList.isNullOrEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(languageList) { language ->
                        LanguageCard(language)
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(bottom = 26.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            DefaultButton("Choose") { }
        }
    }
}