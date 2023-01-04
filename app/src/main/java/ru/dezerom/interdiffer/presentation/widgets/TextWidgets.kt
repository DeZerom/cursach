package ru.dezerom.interdiffer.presentation.widgets

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import ru.dezerom.interdiffer.presentation.utils.Dimens

@Composable
fun HeadingCenteredText(
    text: String,
    modifier: Modifier
) {
    Text(
        text = text,
        modifier = modifier,
        color = Color.Black,
        fontSize = Dimens.FontSizes.heading,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Center
    )
}

@Composable
fun BaseBigCenteredText(
    text: String,
    modifier: Modifier,
    textColor: Color = Color.Black
) {
    Text(
        text = text,
        modifier = modifier,
        color = textColor,
        fontSize = Dimens.FontSizes.base,
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Center
    )
}
