package com.example.internshiptask.view.screens


import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.internshiptask.R
import com.example.internshiptask.data.model.main.Data
import com.example.internshiptask.data.model.main.MAnime
import com.example.internshiptask.data.utils.UiState
import com.example.internshiptask.view.components.core.AppBar
import com.example.internshiptask.view.components.core.ShimmerImage
import com.example.internshiptask.view.navigation.InternshipTaskScreens
import com.example.internshiptask.vm.HomeScreenViewModel
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
                navController.navigate(InternshipTaskScreens.HomeScreen.route) {
                    popUpTo(InternshipTaskScreens.StatsScreen.route) {
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

                Text(
                    text = animeData.title.toString(),
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Studio: ${animeData.studio}",
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.labelSmall,
                    fontStyle = FontStyle.Italic
                )
                Text(
                    text = "Episodes: ${animeData.episodes!!}",
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.labelSmall,
                    fontStyle = FontStyle.Italic
                )
                Text(
                    text = "Released: ${animeData.year}",
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.labelSmall,
                    fontStyle = FontStyle.Italic
                )
                Text(
                    text = "Status: ${animeData.status!!}",
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