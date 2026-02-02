package com.profs.languageapp.presentation.screens.profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
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
    themeViewModel: ThemeViewModel,
    viewModel: ProfileViewModel
) {
    val user by viewModel.currentUser.collectAsState()

    val changeState by viewModel.changeState.collectAsState()
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()
    val colors = MaterialTheme.colorScheme

    val switchThemeText = if (isDarkTheme) {
        stringResource(R.string.switch_to_light)
    } else {
        stringResource(R.string.switch_to_dark)
    }

    val galleryLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            val uri = result.data?.data
            if (uri != null) {
                viewModel.onImagePicked(uri)
                navController.navigate(Destinations.ProfileResizePhoto)
            }
        }

    val cameraLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.TakePicture()
        ) { success ->
            if (success) {
                navController.navigate(Destinations.ProfileResizePhoto)
            }
        }

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
                        AsyncImage(
                            model = user?.image,
                            contentDescription = null,
                            modifier = Modifier.size(134.dp).clip(RoundedCornerShape(67.dp))
                        )
                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            stringResource(R.string.your_profile, "${user?.firstName}"),
                            style = Typography.titleLarge.copy(color = DefaultWhite)
                        )

                        Spacer(modifier = Modifier.height(20.dp))
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(231.dp),
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DeepBlue)
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .padding(bottom = 26.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DefaultButton(switchThemeText) {
                    themeViewModel.toggleTheme()
                }

                Spacer(modifier = Modifier.height(10.dp))

                DefaultButton(stringResource(R.string.change_language)) {
                    navController.navigate(Destinations.LanguageSelect)
                }

                Spacer(modifier = Modifier.height(10.dp))

                DefaultButton(stringResource(R.string.change_image)) {
                    viewModel.openChangeImageDialog()
                }

                Spacer(modifier = Modifier.height(10.dp))

                DefaultButton(stringResource(R.string.logout)) {
                    navController.navigate(Destinations.Login) {
                        popUpTo(0) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            }
        }

        if (changeState) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .background(colors.secondary, RoundedCornerShape(16.dp))
                        .padding(horizontal = 20.dp)
                ) {
                    Spacer(modifier = Modifier.height(20.dp))

                    DefaultButton(stringResource(R.string.from_gallery)) {
                        val intent = viewModel.getGalleryIntent()
                        galleryLauncher.launch(intent)
                        viewModel.closeChangeImageDialog()
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    DefaultButton(stringResource(R.string.take_photo)) {
                        val uri = viewModel.createCameraUri()
                        cameraLauncher.launch(uri)
                        viewModel.closeChangeImageDialog()
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    DefaultButton(stringResource(R.string.cancel)) {
                        viewModel.closeChangeImageDialog()
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                }
            }
        }
    }
}