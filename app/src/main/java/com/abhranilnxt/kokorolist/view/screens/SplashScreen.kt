package com.abhranilnxt.kokorolist.view.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.abhranilnxt.kokorolist.R
import com.abhranilnxt.kokorolist.ui.theme.highlightColor
import com.abhranilnxt.kokorolist.view.components.core.AppNameHeader
import com.abhranilnxt.kokorolist.view.navigation.KokoroListScreens
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {



    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash_animation))
    val scale = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = true, block = {
        scale.animateTo(targetValue = 1f,
            animationSpec = tween(durationMillis = 800,
                easing = {
                    OvershootInterpolator(5f).getInterpolation(it)
                })
        )
        delay(2000L)
        if(FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()) {
            navController.navigate(KokoroListScreens.LoginScreen.route) {
                popUpTo(KokoroListScreens.SplashScreen.route){
                    inclusive = true
                    saveState = true
                }
            }
        }
        else {
            navController.navigate(KokoroListScreens.HomeScreen.route) {
                popUpTo(KokoroListScreens.SplashScreen.route) {
                    inclusive = true
                    saveState = true
                }
            }
        }

    })

    Surface(modifier = Modifier
        .padding(16.dp)
        .size(320.dp)
        .scale(scale.value),
        shape = CircleShape,
        color = Color.Transparent,
        border = BorderStroke(width = 2.dp,
            color = highlightColor)
    ) {

        Card(
            modifier = Modifier
                .fillMaxSize(),
            shape = RectangleShape
        ) {
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Column(modifier = Modifier
            .padding(1.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            LottieAnimation(composition = composition,
                modifier = Modifier.size(128.dp),
                contentScale = ContentScale.Fit)
            AppNameHeader(fontSize = 36)
        }
    }
}