package com.abhranilnxt.kokorolist.view.components.update

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abhranilnxt.kokorolist.data.model.main.MAnime
import com.abhranilnxt.kokorolist.ui.theme.highlightColor
import com.abhranilnxt.kokorolist.ui.theme.poppinsFamily
import com.abhranilnxt.kokorolist.ui.theme.primaryColor
import com.abhranilnxt.kokorolist.view.components.core.ShimmerImage

@Composable
fun CardListItem(anime: MAnime, onPressDetails: () -> Unit) {
    OutlinedCard(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
        colors = CardDefaults.cardColors(primaryColor),
        border = BorderStroke(1.dp, highlightColor),
        elevation = CardDefaults.cardElevation(16.dp),
        shape = RoundedCornerShape(16.dp)) {

        Row(modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.Top) {
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                ShimmerImage(imgUrl = anime.imgUrl.toString(),
                    modifier = Modifier
                        .height(260.dp)
                        .fillMaxWidth(0.5f))
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = anime.title.toString(),
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Medium,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 24.sp,
                    maxLines = 3,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Studio: ${anime.studio}",
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1)
                Text(text = "Episodes: ${anime.episodes!!}",
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1)
                Text(text = "Released: ${anime.year}",
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1)
                Text(text = "Status: ${anime.status!!}",
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1)
                Text(text = "MAL Score: ${anime.malscore!!}",
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1)
                Text(text = "Genres: ${anime.genres}",
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White,
                    overflow = TextOverflow.Ellipsis)
            }

        }
    }
}