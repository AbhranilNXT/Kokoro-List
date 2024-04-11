package com.abhranilnxt.kokorolist.view.screens

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.abhranilnxt.kokorolist.R
import com.abhranilnxt.kokorolist.view.components.search.AnimeList
import com.abhranilnxt.kokorolist.view.components.core.AppBar
import com.abhranilnxt.kokorolist.view.components.search.SearchForm
import com.abhranilnxt.kokorolist.view.navigation.KokoroListScreens
import com.abhranilnxt.kokorolist.vm.AnimeSearchViewModel

@Composable
fun SearchScreen(navController: NavController,
                 viewModel: AnimeSearchViewModel = hiltViewModel()) {

    BackHandler {
        navController.navigate(KokoroListScreens.HomeScreen.route){
            popUpTo(KokoroListScreens.SearchScreen.route) {
                inclusive = true
                saveState = true
            }
        }
    }

    Scaffold(topBar = {
        AppBar(
            title = "Search Anime",
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            navController = navController,
            showProfile = false
        ) {
            navController.navigate(KokoroListScreens.HomeScreen.route){
                popUpTo(KokoroListScreens.SearchScreen.route) {
                    inclusive = true
                    saveState = true
                }
            }
        }
    }) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            shape = RectangleShape
        ) {
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Surface(modifier = Modifier.padding(paddingValues = it),
            color = Color.Transparent) {
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
                Spacer(modifier = Modifier.height(12.dp))

                AnimeList(navController)

            }
        }
    }
}