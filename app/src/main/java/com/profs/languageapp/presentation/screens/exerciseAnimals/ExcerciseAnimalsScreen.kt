package com.profs.languageapp.presentation.screens.exerciseAnimals

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.profs.languageapp.R
import com.profs.languageapp.presentation.composable.DefaultButton
import com.profs.languageapp.presentation.composable.DefaultTextField
import com.profs.languageapp.presentation.screens.game.ExcersiseViewModel
import com.profs.languageapp.presentation.theme.DeepBlue
import com.profs.languageapp.presentation.theme.DefaultWhite
import com.profs.languageapp.presentation.theme.Green
import com.profs.languageapp.presentation.theme.Red
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
    val animalPage by viewModel.animalPage.collectAsState()
    val language by viewModel.language.collectAsState()

    val colors = MaterialTheme.colorScheme
    val scope = rememberCoroutineScope()

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        stringResource(R.string.guess),
                        style = Typography.titleLarge.copy(color = DefaultWhite),
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(92.dp),
            navigationIcon = {
                IconButton(onClick = {
                    navController.navigateUp()
                }) {
                    Icon(
                        painter = painterResource(R.drawable.icon_back_squared),
                        tint = DefaultWhite,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = if (animalPage == 0) DeepBlue else if (animalPage == 1) Green else Red)
        )
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (animalPage == 0) {
                Spacer(modifier = Modifier.height(17.dp))

                AsyncImage(
                    model = simple?.image,
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.clip(RoundedCornerShape(20.dp))
                )
                Spacer(modifier = Modifier.height(17.dp))

                Text(
                    stringResource(R.string.write_who_is_on_image),
                    style = Typography.bodyLarge.copy(
                        color = colors.secondary,
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp,
                        lineHeight = 20.sp,
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                DefaultTextField(
                    label = "",
                    value = simpleAnswer,
                    type = "",
                    isError = false
                ) { viewModel.onSimpleAnswerChange(it) }

                Spacer(modifier = Modifier.height(17.dp))

                DefaultButton(stringResource(R.string.check)) {
                    scope.launch {
                        val success = viewModel.checkSimpleAnswer(simpleAnswer)
                        if (success) {
                            Log.e("HELL YEAH", "RIGHT ANSWER")
                            viewModel.modifyUserRating(100)
                            viewModel.savePage(1)
                        } else {
                            Log.e("NOPE", "WRONG ANSWER")
                            viewModel.savePage(2)
                        }
                        viewModel.onSimpleAnswerChange("")
                    }
                }


            }
            if (animalPage == 1) {
                Spacer(modifier = Modifier.height(52.dp))

                Image(
                    painterResource(R.drawable.answer_right),
                    contentDescription = null,
                    modifier = Modifier.size(160.dp)
                )

                Spacer(modifier = Modifier.height(52.dp))

                Text(
                    stringResource(R.string.right_answer_title),
                    style = Typography.displayMedium.copy(color = colors.secondary)
                )

                Spacer(modifier = Modifier.height(42.dp))

                DefaultButton(stringResource(R.string.next)) {
                    viewModel.nextSimple()
                    viewModel.savePage(0)
                }
            }

            if (animalPage == 2) {
                Spacer(modifier = Modifier.height(52.dp))

                Image(
                    painterResource(R.drawable.answer_wrong),
                    contentDescription = null,
                    modifier = Modifier.size(160.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    stringResource(R.string.wrong_answer_title),
                    style = Typography.displayMedium.copy(color = colors.secondary)
                )
                Text(
                    stringResource(
                        R.string.wrong_answer_description,
                        (if (language == "en") simple?.enAnswer else simple?.ruAnswer)!!
                    ), style = Typography.displayMedium.copy(color = colors.secondary)
                )

                Spacer(modifier = Modifier.height(18.dp))

                DefaultButton(stringResource(R.string.next)) {
                    viewModel.nextSimple()
                    viewModel.savePage(0)
                }

                Spacer(modifier = Modifier.height(11.dp))

                DefaultButton(stringResource(R.string.try_again)) { viewModel.savePage(0) }

            }
        }
    }
}