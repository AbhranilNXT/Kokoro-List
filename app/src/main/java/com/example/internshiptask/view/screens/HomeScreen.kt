package com.example.internshiptask.view.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.internshiptask.view.components.AppBar
import com.example.internshiptask.view.components.FABContent
import com.example.internshiptask.view.components.HomeContent
import com.example.internshiptask.view.navigation.InternshipTaskScreens

@Composable
fun HomeScreen(navController: NavController) {
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
            HomeContent(navController)
        }
    }
}