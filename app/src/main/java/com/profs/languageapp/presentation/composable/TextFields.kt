package com.profs.languageapp.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.profs.languageapp.R
import com.profs.languageapp.presentation.theme.Dark
import com.profs.languageapp.presentation.theme.DefaultWhite
import com.profs.languageapp.presentation.theme.GrayDark
import com.profs.languageapp.presentation.theme.Typography

@Composable
fun DefaultTextField(label: String, passwordType: Boolean, onValueChange: (String) -> Unit) {
    var textFieldValue by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }

    val emailPattern = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")

    OutlinedTextField(
        value = textFieldValue,
        onValueChange = {
            textFieldValue = it
            onValueChange(it)
        },
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .onFocusChanged { focusState -> isFocused = focusState.isFocused }
            .clip(RoundedCornerShape(16.dp))
            .background(Dark.copy(alpha = 0.05f), RoundedCornerShape(16.dp)),
        singleLine = true,
        label = {
            if (!isFocused && textFieldValue.isEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(bottom = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        label,
                        style = Typography.bodyLarge.copy(
                            fontSize = 15.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight.Normal,
                            color = GrayDark.copy(alpha = 0.5f)
                        )
                    )
                }
            }
        },
        trailingIcon = {
            if (passwordType) {
                if (textFieldValue.isNotEmpty()) {
                    Row(
                        modifier = Modifier.fillMaxHeight(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { passwordVisible = !passwordVisible },
                            modifier = Modifier
                                .size(20.dp)
                                .padding(bottom = 4.dp)
                        ) {
                            Icon(
                                painter = painterResource(if (passwordVisible) R.drawable.eye else R.drawable.eye),
                                contentDescription = null,
                                tint = GrayDark.copy(alpha = 0.5f)
                            )
                        }
                    }
                }
            }
        }, colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = Dark.copy(alpha = 0.05f),
            focusedContainerColor = GrayDark.copy(alpha = 0.05f),
            unfocusedTextColor = GrayDark.copy(alpha = 0.5f),
            focusedTextColor = Dark,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            cursorColor = GrayDark,
            unfocusedLabelColor = GrayDark.copy(alpha = 0.5f),
            focusedLabelColor = GrayDark.copy(alpha = 0.05f)
        ),
        visualTransformation = if (passwordType && !passwordVisible)
            PasswordVisualTransformation()
        else
            VisualTransformation.None
    )

    if (passwordType && textFieldValue.length < 8 && isFocused) {
        Spacer(Modifier.height(16.dp))

        Text(
            "Forgot Password",
            style = Typography.bodyMedium.copy(fontSize = 14.sp, lineHeight = 16.sp)
        )
    }


}