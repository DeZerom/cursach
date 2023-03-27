package ru.dezerom.interdiffer.presentation.toolbar

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.dezerom.interdiffer.presentation.utils.FullWidthModifier
import ru.dezerom.interdiffer.presentation.utils.res.Dimens
import ru.dezerom.interdiffer.presentation.widgets.BoldExtraBigText
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
        verticalArrangement = Arrangement.spacedBy(Dimens.Paddings.halfPadding),
        modifier = Modifier
            .background(
                color = Color.White,
                shape = bottomRoundedShape(Dimens.Sizes.defaultCornerRadius)
            )
            .padding(top = Dimens.Paddings.halfPadding)
    ) {
        Box(
            modifier = FullWidthModifier
        ) {
            if (showBackButton) {
                Image(
                    painter = painterResource(id = ru.dezerom.interdiffer.R.drawable.ic_back),
                    contentDescription = stringResource(ru.dezerom.interdiffer.R.string.back),
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .clickable { onBackButtonClick() }
                        .padding(horizontal = Dimens.Paddings.halfPadding)
                )
            }

            BoldExtraBigText(
                text = title,
                Modifier.align(Alignment.Center)
            )
        }

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
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(horizontal = Dimens.Paddings.basePadding)
            .fillMaxWidth()
            .horizontalScroll(state = rememberScrollState())
    ) {
        pagesTitles.forEachIndexed { index, pageTitle ->
            Page(
                title = stringResource(id = pageTitle),
                index = index,
                pagerState = pagerState
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Page(
    title: String,
    index: Int,
    pagerState: PagerState
) {
    val isSelected = index == pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(horizontal = Dimens.Paddings.halfPadding)
            .padding(bottom = Dimens.Paddings.smallPadding)
            .clickable {
                coroutineScope.launch { pagerState.animateScrollToPage(index) }
            }
    ) {
        var textWidth by remember { mutableStateOf(0.dp) }

        val density = LocalDensity.current
        val onTextLayout: (Int) -> Unit = remember {
            {
                with(density) { textWidth = it.toDp() }
            }
        }

        Text(
            text = title,
            color = Color.Black,
            fontWeight = if (isSelected)
                FontWeight.SemiBold
            else
                FontWeight.Normal,
            onTextLayout = remember { { onTextLayout(it.size.width) } }
        )

        if (isSelected) {
            Divider(
                color = Orange,
                thickness = Dimens.Sizes.dividerThickness,
                modifier = Modifier.width(textWidth)
            )
        }
    }
}