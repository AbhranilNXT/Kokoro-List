package com.abhranilnxt.kokorolist.view.components.login

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.abhranilnxt.kokorolist.ui.theme.baseColor
import com.abhranilnxt.kokorolist.ui.theme.highlightColor
import com.abhranilnxt.kokorolist.ui.theme.poppinsFamily

@Composable
fun SubmitButton(
    textId: String,
    loading: Boolean,
    validInputs: Boolean,
    onCLick: () -> Unit
) {

    Button(
        onClick = onCLick,
        modifier = Modifier
            .padding(start = 24.dp, end = 24.dp)
            .fillMaxWidth(),
        enabled = !loading && validInputs,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = highlightColor,
            disabledContainerColor = highlightColor.copy(0.6f))
    ) {

        if (loading) CircularProgressIndicator(modifier = Modifier.size(24.dp))
        else Text(text = textId,
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Medium,
            color = baseColor,
            modifier = Modifier.padding(4.dp))
    }
}