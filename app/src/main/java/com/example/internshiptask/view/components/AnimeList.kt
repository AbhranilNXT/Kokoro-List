package com.example.internshiptask.view.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.internshiptask.data.local.UiState
import com.example.internshiptask.vm.AnimeSearchViewModel

@Composable
fun AnimeList(navController: NavController, viewModel:AnimeSearchViewModel = hiltViewModel()) {

    val animeList = viewModel.listOfAnime.collectAsState().value
    when(animeList)
    {
        is UiState.Idle -> {
            viewModel.loadAnime(query = "Jujutsu Kaisen")
        }
        is UiState.Loading -> {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
        is UiState.Success -> {
            val listOfAnime = animeList.data.data
            LazyColumn(modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(listOfAnime.size) {
                    AnimeRow(listOfAnime[it], navController)
                }
            }
        }
        else -> {}
    }
}