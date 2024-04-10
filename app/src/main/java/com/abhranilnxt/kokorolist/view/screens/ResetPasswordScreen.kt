package com.abhranilnxt.kokorolist.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.abhranilnxt.kokorolist.data.utils.isValidEmail
import com.abhranilnxt.kokorolist.data.utils.showToast
import com.abhranilnxt.kokorolist.view.components.login.EmailInput
import com.abhranilnxt.kokorolist.view.components.login.SubmitButton
import com.abhranilnxt.kokorolist.view.navigation.KokoroListScreens
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun ResetPasswordScreen(navController: NavController) {

    val context = LocalContext.current
    val auth = Firebase.auth
    val email = rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequest = FocusRequester.Default
    val valid = remember(email.value) {
        email.value.trim().isNotEmpty() && isValidEmail(email.value)
    }
    val modifier = Modifier
        .height(280.dp)
        .background(MaterialTheme.colorScheme.background)
        .verticalScroll(rememberScrollState())

    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailInput(emailState = email,
            enabled = true,
            onAction = KeyboardActions.Default)

        SubmitButton(
            textId = "Send Reset Email",
            loading = false,
            validInputs = true
        ) {
            auth.sendPasswordResetEmail(email.value)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        showToast(context, "Reset Password Email Sent!")
                        keyboardController?.hide()
                        navController.navigate(KokoroListScreens.LoginScreen.route) {
                            popUpTo(KokoroListScreens.ResetPasswordScreen.route) {
                                inclusive = true
                                saveState = true
                            }
                        }
                    } else {
                        showToast(context, "Failed!")
                    }
                }
        }
    }
}