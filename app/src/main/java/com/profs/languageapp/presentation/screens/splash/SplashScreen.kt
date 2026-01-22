package com.profs.languageapp.presentation.screens.splash

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.profs.languageapp.R
import com.profs.languageapp.data.utils.Destinations
import com.profs.languageapp.presentation.screens.onboarding.OnboardingViewModel
import com.profs.languageapp.presentation.theme.DeepBlue
import com.profs.languageapp.presentation.theme.DefaultWhite
import com.profs.languageapp.presentation.theme.Fredoka
import com.profs.languageapp.presentation.theme.Typography

@Composable
fun SplashScreen(
    navController: NavHostController,
    viewModel: OnboardingViewModel
) {
    val skipOnboarding by viewModel.completed.collectAsState(initial = false)
    val isLanguageSelected by viewModel.isLanguageSelected.collectAsState(initial = false)

    val offsetY = remember { Animatable(0f) }
    val scale = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1.2f,
            animationSpec = tween(800)
        )

        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(400, easing = FastOutSlowInEasing)
        )

        offsetY.animateTo(
            targetValue = -2000f,
            animationSpec = tween(durationMillis = 900, easing = FastOutSlowInEasing)
        )

        val destination = when {
            !skipOnboarding -> Destinations.Onboarding
            !isLanguageSelected -> Destinations.LanguageSelect
            else -> Destinations.Signup
        }

        navController.navigate(destination) {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepBlue),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset { IntOffset(0, offsetY.value.toInt()) },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.logo_main),
                contentDescription = null,
                modifier = Modifier
                    .size(164.dp)
                    .graphicsLayer(
                        scaleX = scale.value, scaleY = scale.value
                    )
            )

            Text(
                "Language App",
                fontFamily = Fredoka,
                fontWeight = FontWeight.SemiBold,
                fontSize = 36.sp,
                lineHeight = 42.sp,
                letterSpacing = 0.sp,
                color = DefaultWhite,
                modifier = Modifier.padding(top = 240.dp)
            )
        }
    }
}