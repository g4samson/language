package com.profs.languageapp.presentation.screens.onboarding

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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.profs.languageapp.R
import com.profs.languageapp.data.utils.Destinations
import com.profs.languageapp.presentation.composable.DefaultButton
import com.profs.languageapp.presentation.theme.Typography
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    navController: NavHostController,
    viewModel: OnboardingViewModel
) {
    val scope = rememberCoroutineScope()

    val savedPage by viewModel.page.collectAsState()
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { viewModel.pages.size }
    )

    LaunchedEffect(savedPage) {
        pagerState.scrollToPage(savedPage)
    }

    val colors = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier, userScrollEnabled = false
        ) { page ->
            val item = viewModel.pages[page]

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(140.dp))

                Image(
                    painter = painterResource(id = item.image),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 44.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(100.dp))

                Image(
                    painterResource(item.ind),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    contentScale = ContentScale.FillWidth
                )

                Spacer(modifier = Modifier.height(40.dp))

                Text(
                    text = stringResource(item.title),
                    style = Typography.titleLarge.copy(color = colors.primary),
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(item.body),
                    style = Typography.bodyMedium.copy(
                        color = colors.primary.copy(alpha = 0.6f),
                        fontSize = 15.sp,
                        lineHeight = 20.sp,
                        textAlign = TextAlign.Center
                    )
                )

                Spacer(modifier = Modifier.height(50.dp))

                DefaultButton(stringResource(item.btn)) {
                    scope.launch {
                        val nextPage = pagerState.currentPage + 1
                        if (nextPage < viewModel.pages.size) {
                            pagerState.animateScrollToPage(nextPage)
                            viewModel.saveCurrentPage(nextPage)
                        } else {
                            viewModel.completeOnboarding()
                            navController.navigate(Destinations.LanguageSelect) {
                                popUpTo(navController.graph.startDestinationId) { inclusive = true }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    stringResource(R.string.skip_onb),
                    style = Typography.bodyMedium.copy(color = colors.primary), modifier = Modifier.clickable {
                        scope.launch {
                            viewModel.completeOnboarding()
                            navController.navigate(Destinations.LanguageSelect) {
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                )

                Spacer(modifier = Modifier.height(40.dp))

            }
        }
    }
}