package com.profs.languageapp.presentation.screens.login

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.profs.languageapp.R
import com.profs.languageapp.data.utils.Destinations
import com.profs.languageapp.presentation.composable.DefaultButton
import com.profs.languageapp.presentation.composable.DefaultTextField
import com.profs.languageapp.presentation.theme.Blue
import com.profs.languageapp.presentation.theme.Dark
import com.profs.languageapp.presentation.theme.DarkLighter
import com.profs.languageapp.presentation.theme.DeepBlue
import com.profs.languageapp.presentation.theme.DefaultWhite
import com.profs.languageapp.presentation.theme.GrayDark
import com.profs.languageapp.presentation.theme.Red
import com.profs.languageapp.presentation.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel
) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Login",
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

            Text("For free, join now and\nstart learning", style = Typography.titleLarge)

            Spacer(modifier = Modifier.height(32.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Email Address", style = Typography.bodyMedium.copy(color = DarkLighter))

                Spacer(modifier = Modifier.height(8.dp))

                DefaultTextField("Email", false) { viewModel.onEmailChange(it) }

                Spacer(modifier = Modifier.height(24.dp))

                Text("Password", style = Typography.bodyMedium.copy(color = DarkLighter))

                Spacer(modifier = Modifier.height(8.dp))

                DefaultTextField("● ● ● ● ● ● ●", true) { viewModel.onPasswordChange(it) }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    "Forgot Password",
                    style = Typography.bodyMedium.copy(color = Red),
                    modifier = Modifier.clickable { })
            }

            Spacer(modifier = Modifier.height(32.dp))

            DefaultButton("Login") { viewModel.onSignIn(onResult = { }) }

            Spacer(modifier = Modifier.height(24.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    "Not you member? ",
                    style = Typography.bodyLarge.copy(
                        color = GrayDark,
                        fontWeight = FontWeight.Normal
                    )
                )
                Text(
                    "Signup",
                    style = Typography.bodyLarge.copy(color = Blue),
                    modifier = Modifier.clickable { navController.navigate(Destinations.Signup) })
            }

        }
    }
}