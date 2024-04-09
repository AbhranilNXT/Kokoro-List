package com.example.internshiptask.view.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.internshiptask.data.model.main.MAnime
import com.example.internshiptask.data.utils.UiState
import com.example.internshiptask.view.components.core.AppBar
import com.example.internshiptask.view.components.update.ShowAnimeUpdate
import com.example.internshiptask.view.components.update.ShowSimpleForm
import com.example.internshiptask.vm.HomeScreenViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun UpdateScreen(navController: NavController,
                 malId: String,
                 viewModel: HomeScreenViewModel = hiltViewModel()) {
    Scaffold(topBar = {
        AppBar(title = "Update Anime",
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            showProfile = false,
            navController = navController) {
            navController.popBackStack()
        }
    }) {
        var listOfAnime = emptyList<MAnime>()
        val currentUser = FirebaseAuth.getInstance().currentUser

        val animeData = viewModel.data.collectAsState().value
        when(animeData)
        {
            is UiState.Idle -> {
                viewModel.getAllAnime()
            }
            is UiState.Loading -> {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
            is UiState.Success -> {
                listOfAnime = animeData.data.toList().filter {
                    it.userId == currentUser?.uid.toString()
                }
                Log.d("Info"," ${listOfAnime.get(0).title}")
                Surface(modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .padding(3.dp)) {
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally) {

                        Surface(modifier = Modifier
                            .padding(2.dp)
                            .fillMaxWidth(),
                            shape = CircleShape,
                            shadowElevation = 4.dp) {
                            ShowAnimeUpdate(animeInfo = animeData.data, animeItemId = malId)
                        }

                        ShowSimpleForm(anime = animeData.data.first {
                            it.malId.toString() == malId
                        }!!, navController)
                    }
                }
            }
            else -> {}
        }
    }
}
