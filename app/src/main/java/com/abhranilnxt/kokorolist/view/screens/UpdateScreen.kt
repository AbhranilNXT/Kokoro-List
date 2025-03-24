package com.abhranilnxt.kokorolist.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.abhranilnxt.kokorolist.R
import com.abhranilnxt.kokorolist.data.utils.UiState
import com.abhranilnxt.kokorolist.ui.theme.poppinsFamily
import com.abhranilnxt.kokorolist.view.components.core.AppBar
import com.abhranilnxt.kokorolist.view.components.update.CardListItem
import com.abhranilnxt.kokorolist.view.components.update.ShowSimpleForm
import com.abhranilnxt.kokorolist.vm.BackendViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun UpdateScreen(navController: NavController,
                 watchlistId: String,
                 viewModel: BackendViewModel = hiltViewModel()) {

    val loadingLottie by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_anim))
    val noInternetLottie by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.no_internet_anim))

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

        val animeData = viewModel.getWatchlistItemState.collectAsState().value
        when(animeData)
        {
            is UiState.Idle -> {
                viewModel.getWatchlistItem(watchlistId)
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
                    Text(text = "Fetching details...",
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Italic,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }
            is UiState.Success -> {
                Surface(modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .padding(3.dp),
                    color = Color.Transparent) {
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally) {

                        CardListItem(anime = animeData.data.data!!)

                        ShowSimpleForm(anime = animeData.data.data, navController)
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
                    Text(text = animeData.message.toString(),
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
}
