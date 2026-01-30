package com.profs.languageapp.presentation.screens.exerciseAnimals

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.profs.languageapp.R
import com.profs.languageapp.data.utils.Destinations
import com.profs.languageapp.presentation.composable.DefaultButton
import com.profs.languageapp.presentation.composable.DefaultTextField
import com.profs.languageapp.presentation.screens.game.ExcersiseViewModel
import com.profs.languageapp.presentation.theme.DeepBlue
import com.profs.languageapp.presentation.theme.DefaultWhite
import com.profs.languageapp.presentation.theme.Typography
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExcerciseAnimalsScreen(
    navController: NavHostController,
    viewModel: ExcersiseViewModel
) {
    val simple by viewModel.currentSimple.collectAsState(initial = null)
    val simpleAnswer by viewModel.simpleAnswer.collectAsState()

    val scope = rememberCoroutineScope()

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        stringResource(R.string.signup),
                        style = Typography.bodyLarge,
                        modifier = Modifier.padding(end = 50.dp, top = 16.dp)
                    )
                }
            }, modifier = Modifier
                .fillMaxWidth()
                .height(102.dp), navigationIcon = {
                IconButton(onClick = {
                    navController.navigateUp()
                }) {
                    Icon(
                        painter = painterResource(R.drawable.icon_back),
                        tint = DefaultWhite,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }, colors = TopAppBarDefaults.topAppBarColors(containerColor = DeepBlue)
        )
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(17.dp))

            AsyncImage(
                model = simple?.image,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.clip(RoundedCornerShape(20.dp))
            )
            Spacer(modifier = Modifier.height(17.dp))

            Text("Write who is on image")

            Spacer(modifier = Modifier.height(8.dp))

            DefaultTextField(
                label = "",
                value = simpleAnswer,
                type = "",
                isError = false
            ) { viewModel.onSimpleAnswerChange(it) }

            Spacer(modifier = Modifier.height(17.dp))

            Text(
                simple?.enAnswer.toString(),
                modifier = Modifier.padding(vertical = 20.dp),
                style = Typography.displayMedium
            )

            Text(
                simple?.ruAnswer.toString(),
                modifier = Modifier.padding(vertical = 20.dp),
                style = Typography.displayMedium
            )


            DefaultButton(stringResource(R.string.check)) {
                scope.launch {
                    val success = viewModel.checkSimpleAnswer(simpleAnswer)
                    if (success) {
                        navController.navigate(Destinations.Profile)
                    } else {
                        navController.navigate(Destinations.ProfileResizePhoto)
                    }
                }
            }


            DefaultButton("next") { viewModel.nextSimple() }
        }
    }
}