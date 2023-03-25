package ru.dezerom.interdiffer.presentation.toolbar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import ru.dezerom.interdiffer.presentation.utils.res.Dimens
import ru.dezerom.interdiffer.ui.theme.Orange
import ru.dezerom.interdiffer.ui.theme.bottomRoundedShape

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerToolbar(
    title: String,
    showBackButton: Boolean = false,
    onBackButtonClick: () -> Unit = {},
    pagesTitles: List<Int>,
    pagerState: PagerState
) {
    Column(
        modifier = Modifier.background(
            color = Color.White,
            shape = bottomRoundedShape(Dimens.Sizes.defaultCornerRadius)
        )
    ) {
        Toolbar(
            title = title,
            showBackButton = showBackButton,
            onBackButtonClick = onBackButtonClick,
            backgroundColor = Color.Transparent
        )

        Pages(pagesTitles = pagesTitles, pagerState = pagerState)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Pages(
    pagesTitles: List<Int>,
    pagerState: PagerState
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimens.Paddings.smallPadding),
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(state = rememberScrollState())
    ) {
        pagesTitles.forEachIndexed { index, pageTitle ->
            Page(
                title = stringResource(id = pageTitle),
                isSelected = index == pagerState.currentPage
            )
        }
    }
}

@Composable
private fun Page(
    title: String,
    isSelected: Boolean
) {
    Column(
        modifier = Modifier.width(IntrinsicSize.Min)
    ) {
        Text(
            text = title,
            color = Color.Black,
            fontWeight = if (isSelected)
                FontWeight.SemiBold
            else
                FontWeight.Normal,
        )

        if (isSelected) {
            Divider(
                color = Orange,
                thickness = Dimens.Sizes.dividerThickness,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}