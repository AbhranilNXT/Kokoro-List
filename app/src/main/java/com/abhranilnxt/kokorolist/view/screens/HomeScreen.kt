package com.abhranilnxt.kokorolist.view.screens

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.abhranilnxt.kokorolist.R
import com.abhranilnxt.kokorolist.view.components.core.AppBar
import com.abhranilnxt.kokorolist.view.components.home.FABContent
import com.abhranilnxt.kokorolist.view.components.home.HomeContent
import com.abhranilnxt.kokorolist.view.navigation.KokoroListScreens
import com.abhranilnxt.kokorolist.vm.HomeScreenViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel:HomeScreenViewModel =  hiltViewModel()) {

    val activity = (LocalContext.current as? Activity)
    BackHandler {
        activity?.finish()
    }

    Scaffold(topBar = {
                      AppBar(title = "KOKORO LIST", navController = navController)
    }, floatingActionButton = {
        FABContent {
            navController.navigate(KokoroListScreens.SearchScreen.route)
        }
    }) {
        paddingValues ->
        //HomeScreenContent
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            shape = RectangleShape
        ) {
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        HomeContent(navController, viewModel)
    }
}