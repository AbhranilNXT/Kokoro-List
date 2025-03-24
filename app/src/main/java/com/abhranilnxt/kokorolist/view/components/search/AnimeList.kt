package com.abhranilnxt.kokorolist.view.components.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.abhranilnxt.kokorolist.R
import com.abhranilnxt.kokorolist.data.utils.UiState
import com.abhranilnxt.kokorolist.ui.theme.poppinsFamily
import com.abhranilnxt.kokorolist.vm.AnimeSearchViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun AnimeList(navController: NavController, viewModel:AnimeSearchViewModel = hiltViewModel()) {

    val animeList = viewModel.listOfAnime.collectAsState().value
    val noInternetLottie by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.no_internet_anim))
    val loadingLottie by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_anim))
    when(animeList)
    {
        is UiState.Idle -> {
            viewModel.loadAnime(query = "")
        }
        is UiState.Loading -> {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                LottieAnimation(
                    composition = loadingLottie,
                    modifier = Modifier.size(220.dp),
                    contentScale = ContentScale.Fit,
                    iterations = LottieConstants.IterateForever
                )
                Text(text = "Searching for anime...",
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Italic,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
        is UiState.Success -> {
            val listOfAnime = animeList.data.data
            LazyColumn(modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(start = 32.dp, end = 32.dp, top = 16.dp, bottom = 16.dp)
            ) {
                items(listOfAnime.size) {
                    AnimeRow(listOfAnime[it], navController)
                }
            }
        }
        is UiState.Error -> {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                LottieAnimation(
                    composition = noInternetLottie,
                    modifier = Modifier.size(220.dp),
                    contentScale = ContentScale.Fit,
                    iterations = LottieConstants.IterateForever
                )
                Text(text = animeList.message.toString(),
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Italic,
                    fontSize = 16.sp,
                    color = Color.Red
                )
            }
        }
    }
}