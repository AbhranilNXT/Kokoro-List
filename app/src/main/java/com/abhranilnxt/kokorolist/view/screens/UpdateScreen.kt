package com.abhranilnxt.kokorolist.view.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.abhranilnxt.kokorolist.R
import com.abhranilnxt.kokorolist.data.model.main.MAnime
import com.abhranilnxt.kokorolist.data.utils.UiState
import com.abhranilnxt.kokorolist.ui.theme.highlightColor
import com.abhranilnxt.kokorolist.ui.theme.primaryColor
import com.abhranilnxt.kokorolist.view.components.core.AppBar
import com.abhranilnxt.kokorolist.view.components.update.ShowAnimeUpdate
import com.abhranilnxt.kokorolist.view.components.update.ShowSimpleForm
import com.abhranilnxt.kokorolist.vm.HomeScreenViewModel
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

        var listOfAnime = emptyList<MAnime>()
        val currentUser = FirebaseAuth.getInstance().currentUser

        val animeData = viewModel.data.collectAsState().value
        when(animeData)
        {
            is UiState.Idle -> {
                viewModel.getAllAnime()
            }
            is UiState.Loading -> {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth(),
                    color = highlightColor,
                    trackColor = primaryColor)
            }
            is UiState.Success -> {
                listOfAnime = animeData.data.toList().filter {
                    it.userId == currentUser?.uid.toString()
                }
                Log.d("Info"," ${listOfAnime.get(0).title}")
                Surface(modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .padding(3.dp),
                    color = Color.Transparent) {
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally) {

                        ShowAnimeUpdate(animeInfo = animeData.data, animeItemId = malId)

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
