package com.abhranilnxt.kokorolist.view.screens

import android.app.Activity
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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.navigation.NavController
import com.abhranilnxt.kokorolist.R
import com.abhranilnxt.kokorolist.ui.theme.highlightColor
import com.abhranilnxt.kokorolist.ui.theme.poppinsFamily
import com.abhranilnxt.kokorolist.view.components.core.AppNameHeader
import com.abhranilnxt.kokorolist.view.components.login.UserForm
import com.abhranilnxt.kokorolist.view.navigation.KokoroListScreens
import com.abhranilnxt.kokorolist.vm.LoginViewModel

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    val activity = (LocalContext.current as? Activity)
    val context = LocalContext.current
    BackHandler {
        activity?.finish()
    }

    val showLoginForm = rememberSaveable { mutableStateOf(true) }

    Surface(modifier = Modifier
        .fillMaxSize()
        .background(Color.Transparent)) {

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

        Column(horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(start = 18.dp)) {

            AppNameHeader(fontSize = 48)
            Text(text = "Find the Anime that Speaks to your Heart 心!",
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 24.sp,
                color = Color.White)

            Spacer(modifier = Modifier.height(160.dp))
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom) {

            if(showLoginForm.value) UserForm(loading = false, isCreateAccount = false,navController) { email, pwd ->
                viewModel.signInWithEmailPass(email, pwd, error = {
                    Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                }){
                    Toast.makeText(context, "Login Successful!", Toast.LENGTH_LONG).show()
                    navController.navigate(KokoroListScreens.HomeScreen.route){
                        popUpTo(KokoroListScreens.LoginScreen.route){
                            inclusive = true
                            saveState = true
                        }
                    }
                }
            }
            else {
                UserForm(loading = false, isCreateAccount = true, navController) { email, pwd ->
                    viewModel.createUserWithEmailPass(email, pwd) {
                        navController.navigate(KokoroListScreens.HomeScreen.route){
                            popUpTo(KokoroListScreens.LoginScreen.route){
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
                Text(text = userText,
                    color = Color.White,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp)
                Text(text = text,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .clickable {
                            showLoginForm.value = !showLoginForm.value
                        }
                        .padding(start = 4.dp),
                    color = highlightColor)
            }
        }
    }
}