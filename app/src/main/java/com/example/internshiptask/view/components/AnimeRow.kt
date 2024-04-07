package com.example.internshiptask.view.components

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
import com.example.internshiptask.view.navigation.InternshipTaskScreens
import com.google.common.base.Strings.isNullOrEmpty

@Composable
fun AnimeRow(anime: Data, navController: NavController) {
    Card(modifier = Modifier
        .clickable {
            navController.navigate(InternshipTaskScreens.DetailsScreen.route + "/${anime.mal_id}")
        }
        .fillMaxWidth()
        .height(168.dp)
        .padding(4.dp),
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(8.dp)) {
        Row(modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.Top) {

            val imgUrl = if(!anime.images.jpg.large_image_url.isNullOrEmpty())
                anime.images.jpg.large_image_url
            else R.string.img404url

            ShimmerImage(imgUrl = imgUrl.toString(), modifier = Modifier
                .width(120.dp)
                .fillMaxHeight()
                .padding(end = 4.dp))

            Column {
                val title = if (!anime.title_english.isNullOrEmpty())
                    anime.title_english
                else anime.title

                val studio = if (!anime.studios.isNullOrEmpty())
                    anime.studios[0].name
                else "Not Found"

                var genre0 = if(!anime.genres.isNullOrEmpty())
                    anime.genres[0].name
                else "Not Found"

                var genreNum = anime.genres.size
                if (genreNum > 1) {
                    for (i in 1 until genreNum) {
                        genre0 += ", ${anime.genres[i].name}"
                    }
                }

                val year = if (anime.year == 0)
                    "Not Yet Released"
                else anime.year

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
                    text = "Episodes: ${anime.episodes!!}",
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
                    text = "Status: ${anime.status!!}",
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.labelSmall,
                    fontStyle = FontStyle.Italic
                )
                Text(
                    text = "MAL Score: ${anime.score!!}",
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