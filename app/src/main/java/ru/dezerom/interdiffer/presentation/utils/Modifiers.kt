package ru.dezerom.interdiffer.presentation.utils

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier

val FullWidthModifier = Modifier
    .fillMaxWidth()

val MaxSizeModifier = Modifier
    .fillMaxSize()

val FullWidthCardModifier = FullWidthModifier
    .padding(Dimens.Paddings.basePadding)

val FullWidthTextModifier = FullWidthModifier
    .padding(horizontal = Dimens.Paddings.basePadding)

