package com.abhranilnxt.kokorolist.view.components.core

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abhranilnxt.kokorolist.ui.theme.baseColor
import com.abhranilnxt.kokorolist.ui.theme.highlightColor
import com.abhranilnxt.kokorolist.ui.theme.poppinsFamily

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(value = valueState.value,
        onValueChange = {valueState.value = it},
        label = { Text(text = labelId,
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Normal)},
        singleLine = isSingleLine,
        textStyle = TextStyle(fontFamily = poppinsFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = Color.White),
        modifier = modifier
            .padding(start = 24.dp, end = 24.dp)
            .fillMaxWidth(),
        enabled = enabled,
        colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = baseColor,
            unfocusedContainerColor = baseColor.copy(0.7f),
            focusedLabelColor = Color.White,
            unfocusedLabelColor = Color.White,
            cursorColor = highlightColor,
            focusedBorderColor = highlightColor,
            unfocusedBorderColor = highlightColor.copy(0.7f)),
        shape = RoundedCornerShape(16.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction
    )
}