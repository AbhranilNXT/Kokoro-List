package com.abhranilnxt.kokorolist.view.components.home

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.abhranilnxt.kokorolist.ui.theme.baseColor
import com.abhranilnxt.kokorolist.ui.theme.highlightColor

@Composable
fun FABContent(onTap: () -> Unit) {
    FloatingActionButton(onClick = { onTap()},
        shape = RoundedCornerShape(50.dp),
        containerColor = highlightColor
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add an Anime",
            tint = baseColor)
    }
}