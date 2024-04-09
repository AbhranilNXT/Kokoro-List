package com.example.internshiptask.view.components.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.internshiptask.R
import com.example.internshiptask.data.model.main.Data
import com.example.internshiptask.view.components.core.ShimmerImage
import com.example.internshiptask.view.navigation.InternshipTaskScreens

@Composable
fun AnimeRow(animeData: Data, navController: NavController) {
    Card(modifier = Modifier
        .clickable {
            navController.navigate(InternshipTaskScreens.DetailsScreen.route + "/${animeData.mal_id}")
        }
        .fillMaxWidth()
        .height(168.dp)
        .padding(4.dp),
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(8.dp)) {
        Row(modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.Top) {

            val imgUrl = if(!animeData.images.jpg.large_image_url.isNullOrEmpty())
                animeData.images.jpg.large_image_url
            else R.string.img404url

            ShimmerImage(imgUrl = imgUrl.toString(), modifier = Modifier
                .width(120.dp)
                .fillMaxHeight()
                .padding(end = 4.dp))

            Column {
                val title = if (!animeData.title_english.isNullOrEmpty())
                    animeData.title_english
                else animeData.title

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

                Text(
                    text = title,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Studio: $studio",
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
                    text = "Released: $year",
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
                    text = "MAL Score: ${animeData.score!!}",
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.labelSmall,
                    fontStyle = FontStyle.Italic
                )
                Text(
                    text = "Genres: $genre0 ",
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.labelSmall,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}