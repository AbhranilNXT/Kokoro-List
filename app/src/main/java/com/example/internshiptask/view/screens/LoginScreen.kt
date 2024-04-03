package com.example.internshiptask.view.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.example.internshiptask.view.components.AppLogo
import com.example.internshiptask.view.components.UserForm

@Composable
fun LoginScreen(navController: NavController) {

    val showLoginForm = rememberSaveable { mutableStateOf(true) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top) {
            AppLogo(modifier = Modifier.padding(top = 24.dp))

            if(showLoginForm.value) UserForm(loading = false, isCreateAccount = false) { email, pwd ->
                //TODO: Login to FB
            }
            else {
                UserForm(loading = false, isCreateAccount = true) { email, pwd ->
                    //TODO: Create FB Account
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.padding(15.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {

                val text = if(showLoginForm.value) "Sign Up" else "Login"
                val userText = if(showLoginForm.value) "New User?" else "Existing User?"
                Text(text = userText)
                Text(text = text,
                    modifier = Modifier.clickable {
                        showLoginForm.value = !showLoginForm.value
                    }.padding(start = 4.dp),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary)
            }
        }
    }
}