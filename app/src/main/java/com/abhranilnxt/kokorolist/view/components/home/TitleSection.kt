package com.abhranilnxt.kokorolist.view.components.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.abhranilnxt.kokorolist.ui.theme.poppinsFamily

@Composable
fun TitleSection(label: String) {

    Surface(color = Color.Transparent) {
        Row(horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = label,
                fontSize = 20.sp,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Left,
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.SemiBold,
                color = Color.White)

            Spacer(modifier = Modifier.fillMaxWidth(0.6f))
        }
    }
}