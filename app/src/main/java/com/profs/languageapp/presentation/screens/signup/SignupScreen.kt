package com.profs.languageapp.presentation.screens.signup

import android.content.Intent
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import com.profs.languageapp.R
import com.profs.languageapp.data.utils.Destinations
import com.profs.languageapp.presentation.composable.Checkbox
import com.profs.languageapp.presentation.composable.DefaultButton
import com.profs.languageapp.presentation.composable.DefaultTextField
import com.profs.languageapp.presentation.theme.Blue
import com.profs.languageapp.presentation.theme.DarkLighter
import com.profs.languageapp.presentation.theme.DeepBlue
import com.profs.languageapp.presentation.theme.DefaultWhite
import com.profs.languageapp.presentation.theme.GrayDark
import com.profs.languageapp.presentation.theme.Typography
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(
    navController: NavHostController,
    viewModel: SignupViewModel
) {
    val email by viewModel.email.collectAsState()
    val firstName by viewModel.firstName.collectAsState()
    val lastName by viewModel.lastName.collectAsState()
    val passwordState by viewModel.passwordState.collectAsState()
    val emailError by viewModel.emailError.collectAsState()
    val passwordError by viewModel.passwordError.collectAsState()

    val context = LocalContext.current


    var checked by remember { mutableStateOf(false) }


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
                IconButton(onClick = { if (passwordState) viewModel.onPasswordStateChange(false) else navController.navigateUp() }) {
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
            if (passwordState == false) {
                Spacer(modifier = Modifier.height(40.dp))

                Text(stringResource(R.string.create_account), style = Typography.titleLarge)

                Spacer(modifier = Modifier.height(32.dp))

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        stringResource(R.string.first_name),
                        style = Typography.bodyMedium.copy(color = DarkLighter)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    DefaultTextField(
                        "Your First Name",
                        "",
                        false
                    ) { viewModel.onFirstNameChange(it) }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        stringResource(R.string.last_name),
                        style = Typography.bodyMedium.copy(color = DarkLighter)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    DefaultTextField("Your Last Name", "", false) { viewModel.onLastNameChange(it) }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        stringResource(R.string.email_address),
                        style = Typography.bodyMedium.copy(color = DarkLighter)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    DefaultTextField(
                        label = "Email",
                        type = "email",
                        isError = emailError,
                    ) {
                        viewModel.onEmailChange(it)
                    }
                }

                Spacer(modifier = Modifier.height(34.dp))

                DefaultButton(stringResource(R.string.continue_btn)) {
                    viewModel.onPasswordStateChange(
                        true
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        stringResource(R.string.already_member) + " ",
                        style = Typography.bodyLarge.copy(
                            color = GrayDark,
                            fontWeight = FontWeight.Normal
                        )
                    )
                    Text(
                        stringResource(R.string.login),
                        style = Typography.bodyLarge.copy(color = Blue),
                        modifier = Modifier.clickable { navController.navigate(Destinations.Login) })
                }

            } else {
                Spacer(modifier = Modifier.height(40.dp))

                Text(stringResource(R.string.choose_password), style = Typography.titleLarge)

                Spacer(modifier = Modifier.height(32.dp))

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        stringResource(R.string.password),
                        style = Typography.bodyMedium.copy(color = DarkLighter)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    DefaultTextField(
                        "● ● ● ● ● ● ●",
                        "password",
                        isError = passwordError
                    ) { viewModel.onPasswordChange(it) }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        stringResource(R.string.confirm_password),
                        style = Typography.bodyMedium.copy(color = DarkLighter)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    DefaultTextField(
                        "● ● ● ● ● ● ●",
                        "password",
                        isError = passwordError
                    ) { viewModel.onConfirmPasswordChange(it) }

                    Spacer(modifier = Modifier.height(25.dp))

                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 5.dp)) {
                        Checkbox { checked = !checked }
                        Text(
                            " " + stringResource(R.string.i),
                            style = Typography.bodyLarge.copy(
                                fontWeight = FontWeight.Normal,
                                color = GrayDark
                            )
                        )
                        Text(
                            " " + stringResource(R.string.have_read),
                            style = Typography.bodyLarge.copy(
                                fontWeight = FontWeight.Normal,
                                color = Blue
                            ), modifier = Modifier.clickable { viewModel.onRulesClick(context) }
                        )
                    }
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 5.dp)) {
                        Text(
                            stringResource(R.string.the_rules), style = Typography.bodyLarge.copy(
                                fontWeight = FontWeight.Normal,
                                color = Blue
                            ), modifier = Modifier.clickable { viewModel.onRulesClick(context) })
                        Text(
                            " " + stringResource(R.string.accept_rules),
                            style = Typography.bodyLarge.copy(
                                fontWeight = FontWeight.Normal,
                                color = GrayDark
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(73.dp))

                DefaultButton(stringResource(R.string.signup)) {
                    viewModel.onPasswordStateChange(
                        true
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        stringResource(R.string.already_member) + " ",
                        style = Typography.bodyLarge.copy(
                            color = GrayDark,
                            fontWeight = FontWeight.Normal
                        )
                    )
                    Text(
                        stringResource(R.string.login),
                        style = Typography.bodyLarge.copy(color = Blue),
                        modifier = Modifier.clickable { navController.navigate(Destinations.Login) })
                }
            }
        }
    }
}