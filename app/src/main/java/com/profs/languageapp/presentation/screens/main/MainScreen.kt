package com.profs.languageapp.presentation.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.profs.languageapp.R
import com.profs.languageapp.data.utils.Destinations
import com.profs.languageapp.presentation.composable.ExcersiseCard
import com.profs.languageapp.presentation.composable.TopUserCard
import com.profs.languageapp.presentation.theme.DeepBlue
import com.profs.languageapp.presentation.theme.DefaultWhite
import com.profs.languageapp.presentation.theme.GrayDark
import com.profs.languageapp.presentation.theme.SomeColorThatIsNOTInList
import com.profs.languageapp.presentation.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    val excersiseList = viewModel.excersiseList.collectAsState(initial = listOf()).value
    val topUserList = viewModel.topUserList.collectAsState(initial = listOf()).value

    val colors = MaterialTheme.colorScheme

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
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
                            modifier = Modifier
                                .size(54.dp)
                                .clickable { navController.navigate(Destinations.Profile) }
                        )
                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            stringResource(R.string.hello_user, "Emil"),
                            style = Typography.titleLarge.copy(color = DefaultWhite)
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            stringResource(R.string.ready_for_learning),
                            style = Typography.bodyLarge.copy(color = SomeColorThatIsNOTInList)
                        )

                        Spacer(modifier = Modifier.height(11.dp))
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(175.dp),
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DeepBlue)
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(11.dp))

            Text(
                stringResource(R.string.top_users),
                style = Typography.displayMedium.copy(color = colors.secondary)
            )

            Spacer(modifier = Modifier.height(5.dp))

            if (topUserList?.isNotEmpty() == true) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(topUserList) { topUser ->
                        TopUserCard(topUser)
                    }
                }
            }

            Spacer(modifier = Modifier.height(11.dp))

            Text(
                stringResource(R.string.available_exercises),
                style = Typography.displayMedium.copy(color = colors.secondary)
            )

            Spacer(modifier = Modifier.height(9.dp))

            if (excersiseList?.isNotEmpty() == true) {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxWidth(),
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(17.dp),
                    horizontalArrangement = Arrangement.spacedBy(21.dp)
                ) {
                    items(excersiseList) { excersise ->
                        ExcersiseCard(excersise)
                    }
                }
            }
        }
    }
}