package com.abhranilnxt.kokorolist.view.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.abhranilnxt.kokorolist.R
import com.abhranilnxt.kokorolist.data.utils.UiState
import com.abhranilnxt.kokorolist.data.model.details.Details
import com.abhranilnxt.kokorolist.data.model.main.MAnime
import com.abhranilnxt.kokorolist.view.components.core.AppBar
import com.abhranilnxt.kokorolist.view.components.core.RoundedButton
import com.abhranilnxt.kokorolist.view.components.core.ShimmerImage
import com.abhranilnxt.kokorolist.view.navigation.KokoroListScreens
import com.abhranilnxt.kokorolist.vm.DetailsViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

@Composable
fun DetailsScreen(navController: NavController, animeId: Int,
                  viewModel: DetailsViewModel = hiltViewModel()) {
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

        Surface(modifier = Modifier
            .padding(paddingValues = it)
            .fillMaxSize()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top) {

                val data = viewModel.animeInfo.collectAsState().value
                when(data)
                {
                    is UiState.Idle -> {
                        viewModel.animeInfo(animeId = animeId)
                    }
                    is UiState.Loading -> {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Loading....",
                                style = MaterialTheme.typography.bodyLarge)
                            Spacer(modifier = Modifier.height(8.dp))
                            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                        }
                    }
                    is UiState.Success -> {
                        AnimeDetails(data = data, navController)

                    }
                    else -> {}
                }
            }
        }
    }
}

@Composable
fun AnimeDetails(data: UiState.Success<Details>, navController: NavController) {

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

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 16.dp, end = 16.dp),
        elevation = CardDefaults.cardElevation(16.dp),
        shape = RoundedCornerShape(16.dp)) {
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){

            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                ShimmerImage(imgUrl = imgUrl.toString(),
                    modifier = Modifier
                        .height(260.dp)
                        .width(200.dp))
            }
            HorizontalDivider(modifier = Modifier.padding(4.dp),
                thickness = 3.dp,
                color = Color.LightGray)

            Text(text = title.toString(),
                style = MaterialTheme.typography.titleLarge,
                overflow = TextOverflow.Ellipsis,
                maxLines = 3,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = "Studio: $studio",
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1)
            Text(text = "Episodes: ${animeData.episodes!!}",
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1)
            Text(text = "Released: $year",
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1)
            Text(text = "Status: ${animeData.status!!}",
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1)
            Text(text = "MAL Score: ${animeData.score!!}",
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1)
            Text(text = "Genres: $genre0",
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1)
            Spacer(modifier = Modifier.height(4.dp))

            Surface(modifier = Modifier
                .height(localDimensions.heightPixels.dp.times(0.09f))
                .padding(4.dp),
                shape = RectangleShape,
                border = BorderStroke(1.dp, Color.Black)
            ) {
                LazyColumn(modifier = Modifier.padding(4.dp)) {
                    items(1) {
                        Text(text = "Synopsis: ${animeData.synopsis!!}",
                            style = MaterialTheme.typography.bodyLarge,
                            overflow = TextOverflow.Ellipsis)
                    }
                }
            }

        }
    }
    Row(modifier = Modifier.padding(4.dp),
        horizontalArrangement = Arrangement.SpaceAround) {
        RoundedButton(label = "Save"){
            val anime = MAnime(
                title = title.toString(),
                studio = studio,
                episodes = animeData?.episodes.toString(),
                year = year.toString(),
                status = animeData?.status.toString(),
                malscore = animeData?.score,
                genres = genre0,
                synopsis = animeData?.synopsis.toString(),
                notes = "",
                imgUrl = imgUrl.toString(),
                rating = 0.0,
                malId = animeId,
                userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
            )
            saveToFirebase(anime, navController)
//            saveToFirebaseRealtime(anime,navController)
        }
        Spacer(modifier = Modifier.width(25.dp))
        RoundedButton(label = "Cancel"){
            navController.popBackStack()
        }
    }
}


fun saveToFirebase(anime: MAnime, navController: NavController) {
    val db = FirebaseFirestore.getInstance()
    val dbCollection = db.collection("anime")

    if(anime.toString().isNotEmpty()) {
        dbCollection.add(anime)
            .addOnSuccessListener {
                val docId = it.id
                dbCollection.document(docId).update(hashMapOf("id" to docId) as Map<String, Any>)
                    .addOnCompleteListener {
                        if(it.isSuccessful) {
                            navController.popBackStack()
                        }
                    }
                    .addOnFailureListener {
                        Log.d("Error FB", "Error updating doc", it)
                    }
            }
    }
}


fun saveToFirebaseRealtime(anime: MAnime, navController: NavController) {
    val db = Firebase.database
    val dbCollection = db.getReference("anime")

    if (anime.toString().isNotEmpty()) {
        dbCollection.child("${anime.userId}").child("${anime.malId}").setValue(anime)
            .addOnSuccessListener {
                navController.popBackStack()
            }

    }
}
