package com.example.internshiptask.view.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.internshiptask.data.local.UiState
import com.example.internshiptask.view.components.AppBar
import com.example.internshiptask.view.navigation.InternshipTaskScreens
import com.example.internshiptask.vm.DetailsViewModel

@Composable
fun DetailsScreen(navController: NavController, animeId: Int,
                  viewModel: DetailsViewModel = hiltViewModel()) {
    Scaffold(topBar = {
        AppBar(title = "Anime Details",
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            showProfile = false,
            navController = navController) {
            navController.navigate(InternshipTaskScreens.SearchScreen.route){
                popUpTo(InternshipTaskScreens.DetailsScreen.route){
                    inclusive = true
                    saveState = true
                }
            }
        }
    }) {

        Surface(modifier = Modifier
            .padding(paddingValues = it)
            .fillMaxSize()) {
            Column(modifier = Modifier.padding(top = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top) {

                val data = viewModel.animeInfo.collectAsState().value
                when(data)
                {
                    is UiState.Idle -> {
                        viewModel.animeInfo(animeId = animeId)
                    }
                    is UiState.Loading -> {
                        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                    }
                    is UiState.Success -> {
                        Text(text = data.data.data.mal_id.toString())
                    }
                    else -> {}
                }
            }
        }
    }
}