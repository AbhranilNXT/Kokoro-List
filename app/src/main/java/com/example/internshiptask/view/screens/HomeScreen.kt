package com.example.internshiptask.view.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.internshiptask.view.components.core.AppBar
import com.example.internshiptask.view.components.home.FABContent
import com.example.internshiptask.view.components.home.HomeContent
import com.example.internshiptask.view.navigation.InternshipTaskScreens
import com.example.internshiptask.vm.HomeScreenViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel:HomeScreenViewModel =  hiltViewModel()) {
    Scaffold(topBar = {
                      AppBar(title = "Anime Watchlist", navController = navController)
    }, floatingActionButton = {
        FABContent {
            navController.navigate(InternshipTaskScreens.SearchScreen.route)
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