package com.abhranilnxt.kokorolist.view.screens

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.abhranilnxt.kokorolist.R
import com.abhranilnxt.kokorolist.data.utils.UiState
import com.abhranilnxt.kokorolist.ui.theme.highlightColor
import com.abhranilnxt.kokorolist.ui.theme.poppinsFamily
import com.abhranilnxt.kokorolist.view.components.core.AppNameHeader
import com.abhranilnxt.kokorolist.view.components.login.UserForm
import com.abhranilnxt.kokorolist.view.navigation.KokoroListScreens
import com.abhranilnxt.kokorolist.vm.BackendViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun LoginScreen(navController: NavController,
                backendViewModel: BackendViewModel = hiltViewModel()) {

    val activity = (LocalContext.current as? Activity)
    val context = LocalContext.current
    val showLoginForm = rememberSaveable { mutableStateOf(true) }
    val verifyTokenState = backendViewModel.verifyTokenState.collectAsState().value
    var navigated by remember { mutableStateOf(false) }

    // Handle Back Press to Exit
    BackHandler {
        activity?.finish()
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
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
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(start = 18.dp)
        ) {
            AppNameHeader(fontSize = 48)
            Text(
                text = "Find the Anime that Speaks to your Heart 心!",
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 24.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(160.dp))
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            // Show Login or Sign Up Form based on condition
            if (showLoginForm.value) {
                UserForm(
                    loading = verifyTokenState is UiState.Loading,
                    isCreateAccount = false,
                    navController
                ) { email, pwd ->
                    signInWithEmailPass(
                        email,
                        pwd,
                        backendViewModel,
                        context
                    )
                }
            } else {
                UserForm(
                    loading = verifyTokenState is UiState.Loading,
                    isCreateAccount = true,
                    navController
                ) { email, pwd ->
                    createUserWithEmailPass(
                        email,
                        pwd,
                        backendViewModel,
                        context
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.padding(15.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val text = if (showLoginForm.value) "Sign Up" else "Login"
                val userText = if (showLoginForm.value) "New User?" else "Existing User?"

                Text(
                    text = userText,
                    color = Color.White,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
                Text(
                    text = text,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .clickable {
                            showLoginForm.value = !showLoginForm.value
                        }
                        .padding(start = 4.dp),
                    color = highlightColor
                )
            }
        }
    }

    // Handle Token Verification State
    when (verifyTokenState) {
        is UiState.Loading -> {
            // Optionally show a loading indicator if needed
        }

        is UiState.Success -> {
            LaunchedEffect(verifyTokenState) {
                if (!navigated) {
                    navigated = true
                    Toast.makeText(context, "Login Successful!", Toast.LENGTH_LONG).show()
                    navController.navigate(KokoroListScreens.HomeScreen.route) {
                        popUpTo(KokoroListScreens.LoginScreen.route) {
                            inclusive = true
                            saveState = true
                        }
                    }
                    backendViewModel.resetState()
                }
            }
        }

        is UiState.Error -> {
            Log.e("LoginScreen", "Error: ${verifyTokenState.message}")
            Firebase.auth.signOut()
        }

        else -> Unit
    }
}

fun signInWithEmailPass(
    email: String,
    pwd: String,
    viewModel: BackendViewModel,
    context: Context
) {
    Firebase.auth.signInWithEmailAndPassword(email, pwd)
        .addOnSuccessListener { result ->
            val user = result.user
            if (user != null) {
                user.getIdToken(true)
                    .addOnSuccessListener { tokenResult ->
                        val idToken = tokenResult.token
                        if (idToken != null) {
                            viewModel.loginUser("Bearer $idToken")
                        } else {
                            Toast.makeText(context, "Login Failed!", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }
        .addOnFailureListener {
            Toast.makeText(context, "Login Failed: ${it.localizedMessage}", Toast.LENGTH_LONG).show()
        }
}

fun createUserWithEmailPass(
    email: String,
    pwd: String,
    viewModel: BackendViewModel,
    context: Context
) {
    Firebase.auth.createUserWithEmailAndPassword(email, pwd)
        .addOnSuccessListener { result ->
            val user = result.user
            if (user != null) {
                user.getIdToken(true)
                    .addOnSuccessListener { tokenResult ->
                        val idToken = tokenResult.token
                        if (idToken != null) {
                            viewModel.loginUser("Bearer $idToken")
                        } else {
                            Toast.makeText(context, "Sign-up Failed!", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }
        .addOnFailureListener {
            Toast.makeText(context, "Sign-up Failed: ${it.localizedMessage}", Toast.LENGTH_LONG).show()
        }
}