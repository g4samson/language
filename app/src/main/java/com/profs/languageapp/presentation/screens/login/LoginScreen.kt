package com.profs.languageapp.presentation.screens.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.profs.languageapp.R
import com.profs.languageapp.data.utils.Destinations
import com.profs.languageapp.presentation.composable.DefaultButton
import com.profs.languageapp.presentation.composable.DefaultTextField
import com.profs.languageapp.presentation.composable.ErrorDialog
import com.profs.languageapp.presentation.composable.NoInternetScreen
import com.profs.languageapp.presentation.theme.DeepBlue
import com.profs.languageapp.presentation.theme.DefaultWhite
import com.profs.languageapp.presentation.theme.Red
import com.profs.languageapp.presentation.theme.Typography
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel
) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val emailError by viewModel.emailError.collectAsState()
    val passwordError by viewModel.passwordError.collectAsState()

    val colors = MaterialTheme.colorScheme

    val scope = rememberCoroutineScope()

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        stringResource(R.string.login),
                        style = Typography.bodyLarge,
                        modifier = Modifier.padding(end = 50.dp, top = 16.dp)
                    )
                }
            }, modifier = Modifier
                .fillMaxWidth()
                .height(102.dp), navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
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
        when (uiState) {

            is LoginUiState.ServerError -> {
                ErrorDialog(
                    message = (uiState as LoginUiState.ServerError).message,
                    onDismiss = {
                        viewModel.resetState()
                    }
                )
            }

            LoginUiState.NoInternet -> {
                NoInternetScreen {
                    viewModel.loginUser()
                }
            }

            LoginUiState.Success -> {
                navController.navigate(Destinations.Main)
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(24.dp))

                    Image(
                        painterResource(R.drawable.image_login),
                        contentDescription = null,
                        modifier = Modifier.width(105.dp),
                        contentScale = ContentScale.FillWidth
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        stringResource(R.string.login_title),
                        style = Typography.titleLarge.copy(color = colors.primary)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            stringResource(R.string.email_address),
                            style = Typography.bodyMedium.copy(color = colors.secondary)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        DefaultTextField(
                            label = "Email",
                            value = email,
                            type = "email",
                            isError = emailError,
                        ) {
                            viewModel.onEmailChange(it)
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            stringResource(R.string.password),
                            style = Typography.bodyMedium.copy(color = colors.secondary)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        DefaultTextField(
                            "● ● ● ● ● ● ●",
                            value = password,
                            "password",
                            isError = passwordError
                        ) { viewModel.onPasswordChange(it) }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            stringResource(R.string.forgot_password),
                            style = Typography.bodyMedium.copy(color = Red),
                            modifier = Modifier.clickable { })
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    DefaultButton(
                        stringResource(R.string.login),
                        enabled = !passwordError && !emailError && password.isNotEmpty() && email.isNotEmpty()
                    ) {
                        scope.launch {
                            viewModel.loginUser()
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Text(
                            stringResource(R.string.not_member) + " ",
                            style = Typography.bodyLarge.copy(
                                color = colors.primary.copy(alpha = 0.6f),
                                fontWeight = FontWeight.Normal
                            )
                        )
                        Text(
                            stringResource(R.string.signup),
                            style = Typography.bodyLarge.copy(color = colors.tertiary),
                            modifier = Modifier.clickable { navController.navigate(Destinations.Signup) })
                    }

                }
            }
        }

    }
}