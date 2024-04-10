package com.example.internshiptask.view.components.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.internshiptask.R
import com.example.internshiptask.data.utils.isValidEmail
import com.example.internshiptask.view.navigation.InternshipTaskScreens

@Composable
fun UserForm(
    loading: Boolean = false,
    isCreateAccount: Boolean = false,
    navController: NavController,
    onDone: (String, String) -> Unit = { email, pwd ->}
) {
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordVisibility = rememberSaveable { mutableStateOf(false) }
    val passwordFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(email.value, password.value) {
        email.value.trim().isNotEmpty() && isValidEmail(email.value) && password.value.trim().isNotEmpty() && password.value.trim().length > 5
    }
    val modifier = Modifier
        .height(280.dp)
        .background(MaterialTheme.colorScheme.background)
        .verticalScroll(rememberScrollState())

    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (isCreateAccount) Text(text = stringResource(id = R.string.create_account),
            modifier = Modifier.padding(4.dp)) else Text(text = "")

        EmailInput(emailState = email,
            enabled = !loading,
            onAction = KeyboardActions { passwordFocusRequest.requestFocus() })

        PasswordInput(
            modifier = Modifier.focusRequester(passwordFocusRequest),
            passwordState = password,
            labelID = "Password",
            enabled = !loading,
            passwordVisibility = passwordVisibility,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onDone(email.value.trim(), password.value.trim())
            })
        Row(horizontalArrangement = Arrangement.End) {
            Spacer(modifier = Modifier.fillMaxWidth(0.6f))
            Text(text = "Reset Password",
                modifier = Modifier
                    .clickable {
                        navController.navigate(InternshipTaskScreens.ResetPasswordScreen.route)
                    }
                    .padding(start = 4.dp),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary)
        }

        SubmitButton(
            textId = if (isCreateAccount) "Create Account" else "Login",
            loading = loading,
            validInputs = valid
        ) {
            onDone(email.value.trim(), password.value.trim())
            keyboardController?.hide()
        }
    }
}

