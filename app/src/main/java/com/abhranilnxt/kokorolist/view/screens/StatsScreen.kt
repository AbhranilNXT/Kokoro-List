package com.abhranilnxt.kokorolist.view.screens


import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.sharp.Person
import androidx.compose.material.icons.sharp.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.abhranilnxt.kokorolist.R
import com.abhranilnxt.kokorolist.data.model.main.MAnime
import com.abhranilnxt.kokorolist.data.utils.UiState
import com.abhranilnxt.kokorolist.data.utils.formatDate
import com.abhranilnxt.kokorolist.view.components.core.AppBar
import com.abhranilnxt.kokorolist.view.components.core.ShimmerImage
import com.abhranilnxt.kokorolist.view.navigation.KokoroListScreens
import com.abhranilnxt.kokorolist.vm.HomeScreenViewModel
import com.google.firebase.auth.FirebaseAuth
import java.util.Locale

@Composable
fun StatsScreen(navController: NavController, viewModel: HomeScreenViewModel = hiltViewModel()) {
    var listOfAnime: List<MAnime> = emptyList()
    val currentUser = FirebaseAuth.getInstance().currentUser

    Scaffold(
        topBar = {
            AppBar(title = "Anime Stats",
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                showProfile = false,
                navController = navController) {
                navController.navigate(KokoroListScreens.HomeScreen.route) {
                    popUpTo(KokoroListScreens.StatsScreen.route) {
                        inclusive = true
                        saveState = true
                    }
                }
            }
        }
    ) {
        Surface(modifier = Modifier.padding(it)) {
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
                    Log.d("Anime" ,"Home Content: ${listOfAnime}")
                }
                else -> {}
            }
            Column {
                Row {
                    Box(modifier = Modifier
                        .size(45.dp)
                        .padding(2.dp)) {
                        Icon(imageVector = Icons.Sharp.Person, contentDescription = "icon")
                    }
                    Text(text = "Hi, ${
                        currentUser?.email.toString().split("@")[0]
                            .uppercase(Locale.ROOT)}")
                }
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                    shape = CircleShape,
                    elevation = CardDefaults.cardElevation(5.dp)) {
                    val animeList = listOfAnime.filter {
                        it.userId == currentUser?.uid.toString() && it.finishedWatching != null
                    }
                    val watchingAnime = listOfAnime.filter {
                        it.startedWatching != null && it.finishedWatching == null
                    }
                    Column(modifier = Modifier.padding(start = 25.dp, bottom = 25.dp),
                        horizontalAlignment = Alignment.Start) {
                        Text(text = "Your Stats", style = MaterialTheme.typography.headlineSmall)
                        HorizontalDivider()
                        Text(text = "You're watching: ${watchingAnime.size} anime")
                        Text(text = "You've watched: ${animeList.size} anime")
                    }
                }

                HorizontalDivider()
                LazyColumn(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    val watchAnime: List<MAnime> = listOfAnime.filter {
                            it.userId == currentUser?.uid.toString() && it.finishedWatching != null
                    }
                    items(watchAnime.size) {
                        AnimeRowStats(animeData = watchAnime[it])
                    }
                }
            }
        }
    }
}

@Composable
fun AnimeRowStats(animeData: MAnime) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(168.dp)
        .padding(4.dp),
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(8.dp)) {
        Row(modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.Top) {

            val imgUrl = if(!animeData.imgUrl.isNullOrEmpty())
                animeData.imgUrl
            else R.string.img404url

            ShimmerImage(imgUrl = imgUrl.toString(), modifier = Modifier
                .width(120.dp)
                .fillMaxHeight()
                .padding(end = 4.dp))

            Column {
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = animeData.title.toString(),
                        overflow = TextOverflow.Ellipsis
                    )
                    if(animeData.rating!! >= 4.0) {
                        Spacer(modifier = Modifier.fillMaxWidth(0.8f))
                        Icon(imageVector = Icons.Sharp.ThumbUp, contentDescription = "Thumbs Up",
                            tint = Color.Green.copy(alpha = 0.5f))
                    } else {Box{}}
                }
                Text(
                    text = "Studio: ${animeData.studio}",
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.labelSmall,
                    fontStyle = FontStyle.Italic
                )
                Text(
                    text = "Started: ${formatDate(animeData.startedWatching!!)}",
                    softWrap = true,
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.labelSmall,
                    fontStyle = FontStyle.Italic
                )
                Text(
                    text = "Finished: ${formatDate(animeData.finishedWatching!!)}",
                    softWrap = true,
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.labelSmall,
                    fontStyle = FontStyle.Italic
                )
                Text(
                    text = "MAL Score: ${animeData.malscore!!}",
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.labelSmall,
                    fontStyle = FontStyle.Italic
                )
                Text(
                    text = "Genres: ${animeData.genres} ",
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.labelSmall,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}