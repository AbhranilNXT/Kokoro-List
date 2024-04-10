package com.abhranilnxt.kokorolist.view.components.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abhranilnxt.kokorolist.ui.theme.poppinsFamily
import com.abhranilnxt.kokorolist.ui.theme.primaryColor

@Composable
fun AnimeRating(score: Int = 0) {
    Surface(modifier = Modifier
        .width(60.dp)
        .height(45.dp)
        .padding(4.dp),
        shape = RoundedCornerShape(56.dp),
        shadowElevation = 8.dp,
        color = primaryColor
    ) {
        Row(modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {

            Text(text = score.toString(),
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.Light,
                fontSize = 17.sp)
            if(score<4)
            {
                Icon(imageVector = Icons.Filled.StarBorder, contentDescription = "Star",
                    modifier = Modifier.padding(4.dp),
                    tint = Color.White)
            }
            else {
                Icon(imageVector = Icons.Filled.Star, contentDescription = "Star",
                    modifier = Modifier.padding(4.dp),
                    tint = Color.Yellow)
            }
        }
    }
}