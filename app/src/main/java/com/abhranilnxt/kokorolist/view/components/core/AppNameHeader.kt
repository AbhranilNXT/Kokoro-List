package com.abhranilnxt.kokorolist.view.components.core

import androidx.compose.foundation.background
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.abhranilnxt.kokorolist.ui.theme.poppinsFamily

@Composable
fun AppNameHeader(modifier: Modifier = Modifier.background(Color.Transparent),
                  fontSize: Int) {
    Text(
        text = "KOKORO LIST",
        modifier = modifier,
        fontFamily = poppinsFamily, fontWeight = FontWeight.Bold,
        fontSize = fontSize.sp,
        color = Color.White,
    )
}