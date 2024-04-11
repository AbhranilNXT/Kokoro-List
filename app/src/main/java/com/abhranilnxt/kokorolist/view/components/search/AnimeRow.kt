package com.abhranilnxt.kokorolist.view.components.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.abhranilnxt.kokorolist.R
import com.abhranilnxt.kokorolist.data.model.main.Data
import com.abhranilnxt.kokorolist.ui.theme.highlightColor
import com.abhranilnxt.kokorolist.ui.theme.poppinsFamily
import com.abhranilnxt.kokorolist.ui.theme.primaryColor
import com.abhranilnxt.kokorolist.view.components.core.ShimmerImage
import com.abhranilnxt.kokorolist.view.navigation.KokoroListScreens

@Composable
fun AnimeRow(animeData: Data, navController: NavController) {
    OutlinedCard(modifier = Modifier
        .clickable {
            navController.navigate(KokoroListScreens.DetailsScreen.route + "/${animeData.mal_id}")
        }
        .fillMaxWidth()
        .height(196.dp)
        .padding(8.dp),
        colors = CardDefaults.cardColors(primaryColor),
        border = BorderStroke(1.dp, highlightColor),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(8.dp)) {
        Row(modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.Top) {

            val imgUrl = if(!animeData.images.jpg.large_image_url.isNullOrEmpty())
                animeData.images.jpg.large_image_url
            else R.string.img404url

            ShimmerImage(imgUrl = imgUrl.toString(), modifier = Modifier
                .fillMaxWidth(0.35f)
                .fillMaxHeight()
                .padding(start = 6.dp, end = 4.dp, top = 4.dp, bottom = 4.dp))

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
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Studio: $studio",
                    overflow = TextOverflow.Clip,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White
                )
                Text(
                    text = "Episodes: ${animeData.episodes!!}",
                    overflow = TextOverflow.Clip,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White
                )
                Text(
                    text = "Released: $year",
                    overflow = TextOverflow.Clip,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White
                )
                Text(
                    text = "Status: ${animeData.status!!}",
                    overflow = TextOverflow.Clip,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White
                )
                Text(
                    text = "MAL Score: ${animeData.score!!}",
                    overflow = TextOverflow.Clip,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White
                )
                Text(
                    text = "Genres: $genre0",
                    overflow = TextOverflow.Clip,
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White

                )
            }
        }
    }
}