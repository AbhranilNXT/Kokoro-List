package com.abhranilnxt.kokorolist.view.components.home

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
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
import com.abhranilnxt.kokorolist.data.model.be.WatchlistItem
import com.abhranilnxt.kokorolist.data.utils.UiState
import com.abhranilnxt.kokorolist.ui.theme.poppinsFamily
import com.abhranilnxt.kokorolist.view.navigation.KokoroListScreens
import com.abhranilnxt.kokorolist.vm.BackendViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun HomeContent(navController: NavController, viewModel: BackendViewModel = hiltViewModel()) {

    val listOfAnime = viewModel.getWatchlistItemsState.collectAsState().value
    val loadingLottie by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_anim))
    val noInternetLottie by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.no_internet_anim))


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, bottom = 2.dp, start = 16.dp, end = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        when (listOfAnime) {
            is UiState.Idle -> {
                viewModel.getWatchlistItems()
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
                    Text(text = "Fetching your watchlist...",
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Italic,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }

            is UiState.Success -> {
                val watchlist = listOfAnime.data.data
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 56.dp),
                    verticalArrangement = Arrangement.Top
                ) {
                    item { Spacer(modifier = Modifier.height(12.dp)) }
                    item { TitleSection(label = "Currently Watching...") }

                    item {
                        HorizontalScrollableComponent(watchlist!!.currentlyWatching) {
                            navController.navigate(KokoroListScreens.UpdateScreen.route + "/$it")
                        }
                    }

                    item { Spacer(modifier = Modifier.height(24.dp)) }
                    item { TitleSection(label = "Plan to Watch") }

                    item {
                        HorizontalScrollableComponent(watchlist!!.planToWatch) {
                            navController.navigate(KokoroListScreens.UpdateScreen.route + "/$it")
                        }
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
                    Text(text = listOfAnime.message.toString(),
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

@Composable
fun HorizontalScrollableComponent(
    listOfAnime: List<WatchlistItem>,
    onCardPressed: (String) -> Unit
) {
    val scrollableState = rememberScrollState()
    val notFoundLottie by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.not_found_anim))
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(320.dp)
            .horizontalScroll(scrollableState)) {

        if(listOfAnime.isNullOrEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                LottieAnimation(
                    composition = notFoundLottie,
                    modifier = Modifier.size(200.dp),
                    contentScale = ContentScale.Fit,
                    iterations = LottieConstants.IterateForever
                )
                Text(text = "No Anime Found.\nAdd an Anime",
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Italic,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        } else {
            for (anime in listOfAnime) {
                ListCard(anime) {
                    onCardPressed(anime.watchlistId.toString())
                }
            }
        }
    }
}
