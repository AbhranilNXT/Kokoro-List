package com.example.internshiptask.view.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.internshiptask.view.components.search.AnimeList
import com.example.internshiptask.view.components.core.AppBar
import com.example.internshiptask.view.components.search.SearchForm
import com.example.internshiptask.view.navigation.InternshipTaskScreens
import com.example.internshiptask.vm.AnimeSearchViewModel

@Composable
fun SearchScreen(navController: NavController,
                 viewModel: AnimeSearchViewModel = hiltViewModel()) {
    Scaffold(topBar = {
        AppBar(
            title = "Search Anime",
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            navController = navController,
            showProfile = false
        ) {
            navController.navigate(InternshipTaskScreens.HomeScreen.route)
        }
    }) {
        Surface(modifier = Modifier.padding(paddingValues = it)) {
            Column {
                SearchForm(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    viewModel = viewModel
                ) { query ->
                    viewModel.loadAnime(query = query)
                    Log.d("QUERY CHECK","Query: $query")
                }
                Spacer(modifier = Modifier.height(16.dp))

                AnimeList(navController)

            }
        }
    }
}