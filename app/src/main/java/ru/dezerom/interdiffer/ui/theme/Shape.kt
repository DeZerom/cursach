package ru.dezerom.interdiffer.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.dezerom.interdiffer.presentation.utils.Dimens

val Shapes = Shapes(
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
)

fun topRoundedShape(
    cornerRadius: Dp = Dimens.Sizes.defaultCornerRadius
) = RoundedCornerShape(topStart = cornerRadius, topEnd = cornerRadius)

fun bottomRoundedShape(
    cornerRadius: Dp = Dimens.Sizes.defaultCornerRadius
) = RoundedCornerShape(bottomStart = cornerRadius, bottomEnd = cornerRadius)
