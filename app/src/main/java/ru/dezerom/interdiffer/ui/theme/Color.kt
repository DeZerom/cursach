package ru.dezerom.interdiffer.ui.theme

import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Orange = Color(0xFFD96F0E)
val OrangeDark = Color(0xFFC25F05)
val Red = Color(0xFFEF1212)
val TextGrey = Color(0xFFB1B1B1)
val Grey = Color(0xFFE9E9E9)

@Composable
fun textFieldColors() =
    TextFieldDefaults.textFieldColors(
        textColor = Color.Black,
        backgroundColor = Color.White,
        cursorColor = Orange,
        errorCursorColor = Red,
        focusedIndicatorColor = Orange,
        errorIndicatorColor = Red
    )
