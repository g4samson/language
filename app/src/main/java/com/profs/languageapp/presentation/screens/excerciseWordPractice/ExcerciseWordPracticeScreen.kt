package com.profs.languageapp.presentation.screens.excerciseWordPractice

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.profs.languageapp.R
import com.profs.languageapp.data.model.RoundType
import com.profs.languageapp.presentation.composable.DefaultButton
import com.profs.languageapp.presentation.screens.game.ExcersiseViewModel
import com.profs.languageapp.presentation.theme.Blue
import com.profs.languageapp.presentation.theme.DeepBlue
import com.profs.languageapp.presentation.theme.DefaultWhite
import com.profs.languageapp.presentation.theme.GrayLight
import com.profs.languageapp.presentation.theme.Green
import com.profs.languageapp.presentation.theme.Orange
import com.profs.languageapp.presentation.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExcerciseWordPracticeScreen(
    navController: NavHostController,
    viewModel: ExcersiseViewModel
) {
    val complex by viewModel.currentComplex.collectAsState(initial = null)
    val language by viewModel.language.collectAsState()

    val colors = MaterialTheme.colorScheme
    val scope = rememberCoroutineScope()
    val roundType by viewModel.roundType.collectAsState()

    val options by viewModel.options.collectAsState()
    val answered by viewModel.answered.collectAsState()

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        stringResource(R.string.word_practice),
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
                        modifier = Modifier.size(27.dp)
                    )
                }
            },
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
            Spacer(modifier = Modifier.height(34.dp))

            Text(
                text = when (roundType) {
                    RoundType.EN_TO_RU -> complex?.enName ?: ""
                    RoundType.RU_TO_EN -> complex?.ruName ?: ""
                },
                style = Typography.displayMedium.copy(
                    fontSize = 28.sp,
                    lineHeight = 34.sp,
                    letterSpacing = 1.5.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = when (roundType) {
                    RoundType.EN_TO_RU -> complex?.enTranscription ?: ""
                    RoundType.RU_TO_EN -> complex?.ruTranscription ?: ""
                },
                style = Typography.bodyLarge.copy(color = colors.primary)
            )

            Spacer(modifier = Modifier.height(35.dp))

            Column {
                options.forEach { option ->
                    val bgColor = when {
                        !answered && option.isSelected -> Blue
                        answered && option.isCorrect -> Green
                        answered && option.isSelected && !option.isCorrect -> Orange
                        else -> GrayLight
                    }

                    DefaultButton(
                        text = option.text,
                        backgroundColor = bgColor
                    ) {
                        viewModel.selectAnswer(option)
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                }
            }


        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(bottom = 27.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            DefaultButton(
                text = if (!answered) stringResource(R.string.check) else stringResource(R.string.next)
            ) {
                if (!answered) {
                    viewModel.checkAnswer()
                } else {
                    viewModel.nextRound()
                }
            }
        }
    }
}