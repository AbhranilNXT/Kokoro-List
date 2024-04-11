package com.abhranilnxt.kokorolist.view.components.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abhranilnxt.kokorolist.data.model.main.MAnime
import com.abhranilnxt.kokorolist.ui.theme.baseColor
import com.abhranilnxt.kokorolist.ui.theme.highlightColor
import com.abhranilnxt.kokorolist.ui.theme.poppinsFamily
import com.abhranilnxt.kokorolist.ui.theme.primaryColor
import com.abhranilnxt.kokorolist.view.components.core.ShimmerImage


@Composable
fun ListCard(anime: MAnime,
             onPressDetails: (String) -> Unit = {}) {

    OutlinedCard(shape = RoundedCornerShape(29.dp),
        border = BorderStroke(2.dp, highlightColor),
        colors = CardDefaults.cardColors(baseColor),
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = Modifier
            .padding(16.dp)
            .height(320.dp)
            .width(200.dp)
            .clickable { onPressDetails.invoke(anime.title.toString()) }
    ) {
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            ShimmerImage(imgUrl = anime.imgUrl.toString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.69f))

            Text(text = anime.title.toString(), modifier = Modifier.padding(start = 4.dp, end = 2.dp),
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.White)

            Text(text = "Studio: ${anime.studio.toString()}", modifier = Modifier.padding(),
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Italic,
                fontSize = 12.sp,
                color = highlightColor)

            val isStartedWatching = remember {
                mutableStateOf(false)
            }

            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                AnimeRating(score = anime.rating?.toInt()!!)

                Surface(modifier = Modifier
                    .padding(4.dp),
                    shape = RoundedCornerShape(56.dp),
                    shadowElevation = 8.dp,
                    color = primaryColor
                ) {
                    isStartedWatching.value = anime.startedWatching != null
                    if(isStartedWatching.value) {
                        Text(text = "Watching",
                            fontFamily = poppinsFamily,
                            fontWeight = FontWeight.Light,
                            fontSize = 12.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(6.dp))
                    } else {
                        Text(text = "Not Started",
                            fontFamily = poppinsFamily,
                            fontWeight = FontWeight.Light,
                            fontSize = 12.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(6.dp))
                    }
                }
            }
        }
    }
}
