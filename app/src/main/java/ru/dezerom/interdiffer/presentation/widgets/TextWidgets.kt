package ru.dezerom.interdiffer.presentation.widgets

import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import ru.dezerom.interdiffer.presentation.utils.FullWidthModifier
import ru.dezerom.interdiffer.presentation.utils.res.Dimens
import ru.dezerom.interdiffer.ui.theme.TextGrey
import ru.dezerom.interdiffer.ui.theme.textFieldColors

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
    textColor: Color = Color.Black,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        text = text,
        modifier = modifier,
        color = textColor,
        fontSize = Dimens.FontSizes.big,
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Center,
        maxLines = maxLines
    )
}

@Composable
fun BaseCenteredText(
    text: String,
    modifier: Modifier = Modifier,
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

@Composable
fun BaseText(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = Color.Black,
    maxLines: Int = Int.MAX_VALUE,
    fontSize: TextUnit = Dimens.FontSizes.base
) {
    Text(
        text = text,
        modifier = modifier,
        color = textColor,
        fontSize = fontSize,
        fontWeight = FontWeight.Medium,
        maxLines = maxLines
    )
}

@Composable
fun NormalText(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = Color.Black,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
        text = text,
        modifier = modifier,
        color = textColor,
        fontSize = Dimens.FontSizes.base,
        fontWeight = FontWeight.Normal,
        textAlign = textAlign,
        maxLines = maxLines
    )
}

@Composable
fun BaseSmallText(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = TextGrey,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
        text = text,
        modifier = modifier,
        color = textColor,
        fontSize = Dimens.FontSizes.small,
        fontWeight = FontWeight.Normal,
        textAlign = textAlign,
        maxLines = maxLines
    )
}

@Composable
fun BaseBoldText(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = Color.Black,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
        text = text,
        modifier = modifier,
        color = textColor,
        fontSize = Dimens.FontSizes.base,
        fontWeight = FontWeight.SemiBold,
        maxLines = maxLines,
        textAlign = textAlign
    )
}

@Composable
fun BoldExtraBigText(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = Color.Black
) {
    Text(
        text = text,
        modifier = modifier,
        color = textColor,
        fontSize = Dimens.FontSizes.extraBig,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Center
    )
}

@Composable
fun BaseFullWidthTextInput(
    value: String,
    onValueChanged: (String) -> Unit,
    label: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    isSingleLine: Boolean = true,
    isError: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChanged(it) },
        label = { BaseSmallText(text = label) },
        placeholder = { BaseText(text = placeholder, textColor = TextGrey) },
        singleLine = isSingleLine,
        modifier = FullWidthModifier.then(modifier),
        colors = textFieldColors(),
        isError = isError
    )
}

