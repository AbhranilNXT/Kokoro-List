package com.example.internshiptask.view.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.internshiptask.view.components.core.AppLogo
import com.example.internshiptask.view.components.login.UserForm
import com.example.internshiptask.view.navigation.InternshipTaskScreens
import com.example.internshiptask.vm.LoginViewModel

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    val showLoginForm = rememberSaveable { mutableStateOf(true) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top) {
            AppLogo(modifier = Modifier.padding(top = 24.dp))

            if(showLoginForm.value) UserForm(loading = false, isCreateAccount = false,navController) { email, pwd ->
                viewModel.signInWithEmailPass(email, pwd){
                    navController.navigate(InternshipTaskScreens.HomeScreen.route){
                        popUpTo(InternshipTaskScreens.LoginScreen.route){
                            inclusive = true
                            saveState = true
                        }
                    }
                }
            }
            else {
                UserForm(loading = false, isCreateAccount = true, navController) { email, pwd ->
                    viewModel.createUserWithEmailPass(email, pwd) {
                        navController.navigate(InternshipTaskScreens.HomeScreen.route){
                            popUpTo(InternshipTaskScreens.LoginScreen.route){
                                inclusive = true
                                saveState = true
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.padding(15.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {

                val text = if(showLoginForm.value) "Sign Up" else "Login"
                val userText = if(showLoginForm.value) "New User?" else "Existing User?"
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = userText)
                Text(text = text,
                    modifier = Modifier
                        .clickable {
                            showLoginForm.value = !showLoginForm.value
                        }
                        .padding(start = 4.dp),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary)
            }
        }
    }
}