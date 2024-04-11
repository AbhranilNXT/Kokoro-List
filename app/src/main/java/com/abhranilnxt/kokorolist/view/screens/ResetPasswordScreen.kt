package com.abhranilnxt.kokorolist.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.abhranilnxt.kokorolist.R
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

    val modifier = Modifier
        .fillMaxSize()
        .background(Color.Transparent)
        .verticalScroll(rememberScrollState())

    Card(
        modifier = Modifier
            .fillMaxSize(),
        shape = RectangleShape
    ) {
        Image(
            painter = painterResource(id = R.drawable.login_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }

    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        EmailInput(emailState = email,
            enabled = true,
            onAction = KeyboardActions.Default)
        
        Spacer(modifier = Modifier.height(12.dp))

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
        Spacer(modifier = Modifier.height(150.dp))
    }
}