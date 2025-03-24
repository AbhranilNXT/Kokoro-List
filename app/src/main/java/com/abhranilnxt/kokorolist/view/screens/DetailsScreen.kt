package com.abhranilnxt.kokorolist.view.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.abhranilnxt.kokorolist.R
import com.abhranilnxt.kokorolist.data.model.be.PostAnimeBody
import com.abhranilnxt.kokorolist.data.model.details.Details
import com.abhranilnxt.kokorolist.data.utils.UiState
import com.abhranilnxt.kokorolist.ui.theme.baseColor
import com.abhranilnxt.kokorolist.ui.theme.highlightColor
import com.abhranilnxt.kokorolist.ui.theme.poppinsFamily
import com.abhranilnxt.kokorolist.ui.theme.primaryColor
import com.abhranilnxt.kokorolist.view.components.core.AppBar
import com.abhranilnxt.kokorolist.view.components.core.RoundedButton
import com.abhranilnxt.kokorolist.view.components.core.ShimmerImage
import com.abhranilnxt.kokorolist.view.components.core.YoutubePlayer
import com.abhranilnxt.kokorolist.view.navigation.KokoroListScreens
import com.abhranilnxt.kokorolist.vm.BackendViewModel
import com.abhranilnxt.kokorolist.vm.DetailsViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun DetailsScreen(navController: NavController, animeId: Int,
                  viewModel: DetailsViewModel = hiltViewModel(),
                  backEndViewModel: BackendViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val addAnimeToWatchlistState = backEndViewModel.addAnimeToWatchlistState.collectAsState().value
    val noInternetLottie by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.no_internet_anim))
    val loadingLottie by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_anim))
    var loadingActive by remember { mutableStateOf(false) }

    LaunchedEffect(addAnimeToWatchlistState) {
        when(addAnimeToWatchlistState) {
            is UiState.Success -> {
                Toast.makeText(context, "Anime added to watchlist", Toast.LENGTH_SHORT).show()
                navController.navigate(KokoroListScreens.SearchScreen.route) {
                    popUpTo(KokoroListScreens.DetailsScreen.route) {
                        inclusive = true
                        saveState = true
                    }
                }
            }
            is UiState.Error -> {
                Toast.makeText(context, addAnimeToWatchlistState.message, Toast.LENGTH_SHORT).show()
            }
            is UiState.Loading -> {
                loadingActive = true
            }
            else -> Unit
        }
    }

    Scaffold(topBar = {
        AppBar(title = "Anime Details",
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            showProfile = false,
            navController = navController) {
            navController.navigate(KokoroListScreens.SearchScreen.route){
                popUpTo(KokoroListScreens.DetailsScreen.route){
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

        val scrollableState = rememberScrollState()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollableState)
                    .then(if (loadingActive) Modifier.blur(12.dp) else Modifier),
                color = Color.Transparent
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    val data = viewModel.animeInfo.collectAsState().value
                    when (data) {
                        is UiState.Idle -> {
                            viewModel.animeInfo(animeId = animeId)
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
                                Text(
                                    text = "Loading anime details...",
                                    fontFamily = poppinsFamily,
                                    fontWeight = FontWeight.Normal,
                                    fontStyle = FontStyle.Italic,
                                    fontSize = 16.sp,
                                    color = Color.White
                                )
                            }
                        }

                        is UiState.Success -> {
                            AnimeDetails(data = data, navController, backEndViewModel)
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
                                Text(
                                    text = data.message.toString(),
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

            if (loadingActive) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        LottieAnimation(
                            composition = loadingLottie,
                            modifier = Modifier.size(220.dp),
                            contentScale = ContentScale.Fit,
                            iterations = LottieConstants.IterateForever
                        )
                        Text(
                            text = "Adding to your watchlist...",
                            fontFamily = poppinsFamily,
                            fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Italic,
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AnimeDetails(data: UiState.Success<Details>, navController: NavController, viewModel: BackendViewModel) {

    val localDimensions = LocalContext.current.resources.displayMetrics
    val animeData = data.data.data
    val animeId = animeData.mal_id

    val title = if (data.data.data.title_english != null)
        animeData.title_english
    else animeData.title

    val imgUrl = if(!animeData.images.jpg.large_image_url.isNullOrEmpty())
        animeData.images.jpg.large_image_url
    else R.string.img404url
    val studio = if (!animeData.studios.isNullOrEmpty())
        animeData.studios[0].name
    else "No Info"
    var genre0 = if(!animeData.genres.isNullOrEmpty())
        animeData.genres[0].name
    else "No Info"
    val genreNum = animeData.genres.size
    if (genreNum > 1) {
        for (i in 1 until genreNum) {
            genre0 += ", ${animeData.genres[i].name}"
        }
    }
    val year = if (animeData.year == 0)
        "No Info"
    else animeData.year

    OutlinedCard(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
        colors = CardDefaults.cardColors(primaryColor),
        border = BorderStroke(1.dp, highlightColor),
        elevation = CardDefaults.cardElevation(16.dp),
        shape = RoundedCornerShape(16.dp)) {
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){

            Row(modifier = Modifier.padding(4.dp),
                verticalAlignment = Alignment.Top) {

                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    ShimmerImage(imgUrl = imgUrl.toString(),
                        modifier = Modifier
                            .height(260.dp)
                            .fillMaxWidth(0.5f))
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = title.toString(),
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Medium,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 24.sp,
                        maxLines = 3,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Studio: $studio",
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Italic,
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1)
                    Text(text = "Episodes: ${animeData.episodes!!}",
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Italic,
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1)
                    Text(text = "Released: $year",
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Italic,
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1)
                    Text(text = "Status: ${animeData.status!!}",
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Italic,
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1)
                    Text(text = "MAL Score: ${animeData.score!!}",
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Italic,
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1)
                    Text(text = "Genres: $genre0",
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Italic,
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White,
                        overflow = TextOverflow.Ellipsis)
                }

            }

            if(!animeData.trailer.youtube_id.isNullOrEmpty()) {
                YoutubePlayer(youtubeId = animeData.trailer.youtube_id,
                    lifecycleOwner = LocalLifecycleOwner.current)
            }


            HorizontalDivider(modifier = Modifier.padding(4.dp),
                thickness = 2.dp,
                color = highlightColor)


            Surface(modifier = Modifier
                .height(localDimensions.heightPixels.dp.times(0.075f))
                .padding(8.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, highlightColor),
                color = baseColor
            ) {
                LazyColumn(modifier = Modifier.padding(4.dp)) {
                    items(1) {
                        Text(text = "Synopsis: ${animeData.synopsis!!}",
                            style = MaterialTheme.typography.bodyLarge,
                            overflow = TextOverflow.Ellipsis,
                            color = highlightColor)
                    }
                }
            }

        }
    }
    Row(modifier = Modifier.padding(4.dp),
        horizontalArrangement = Arrangement.SpaceAround) {
        RoundedButton(label = "Save"){
            val animeBody = PostAnimeBody(
                malId = animeId,
                title = title.toString(),
                studio = studio,
                episodes = animeData?.episodes.toString(),
                year = year.toString(),
                status = animeData?.status.toString(),
                malScore = animeData.score,
                genres = genre0,
                synopsis = animeData?.synopsis.toString(),
                imageUrl = imgUrl.toString()
            )
            viewModel.addAnimeToWatchlist(animeBody)
        }
        Spacer(modifier = Modifier.width(25.dp))
        RoundedButton(label = "Cancel"){
            navController.popBackStack()
        }
    }
}
