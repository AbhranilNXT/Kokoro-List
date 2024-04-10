package com.abhranilnxt.kokorolist.view.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.abhranilnxt.kokorolist.view.components.core.AppBar
import com.abhranilnxt.kokorolist.view.components.home.FABContent
import com.abhranilnxt.kokorolist.view.components.home.HomeContent
import com.abhranilnxt.kokorolist.view.navigation.KokoroListScreens
import com.abhranilnxt.kokorolist.vm.HomeScreenViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel:HomeScreenViewModel =  hiltViewModel()) {
    Scaffold(topBar = {
                      AppBar(title = "Anime Watchlist", navController = navController)
    }, floatingActionButton = {
        FABContent {
            navController.navigate(KokoroListScreens.SearchScreen.route)
        }
    }) {
        paddingValues ->
        //content
        Surface(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()) {
            HomeContent(navController, viewModel)
        }
    }
}