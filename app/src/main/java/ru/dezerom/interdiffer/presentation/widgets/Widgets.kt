package ru.dezerom.interdiffer.presentation.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.dezerom.interdiffer.R
import ru.dezerom.interdiffer.presentation.utils.*
import ru.dezerom.interdiffer.ui.theme.Grey
import ru.dezerom.interdiffer.ui.theme.Orange
import ru.dezerom.interdiffer.ui.theme.Shapes

@Composable
fun BaseColumnWidget(
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = MaxSizeModifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = content
    )
}

@Composable
fun EmptyListWidget(
    onAddButtonClick: () -> Unit
) {
    Card(
        modifier = FullWidthCardModifier,
        shape = Shapes.small,
        backgroundColor = Color.White,
        elevation = Dimens.Elevations.baseElevation
    ) {
        Column(
            modifier = FullWidthModifier.padding(Dimens.Paddings.basePadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_empty_list),
                contentDescription = ""
            )

            HeadingCenteredText(
                text = stringResource(id = R.string.empty_list),
                modifier = FullWidthTextModifier
                    .padding(top = Dimens.Paddings.doublePadding)
            )

            BaseBigCenteredText(
                text = stringResource(id = R.string.empty_list_description),
                modifier = FullWidthTextModifier
                    .padding(top = Dimens.Paddings.largePadding)
            )

            FullWidthButton(
                text = stringResource(id = R.string.add),
                modifier = FullWidthModifier
                    .padding(top = Dimens.Paddings.doublePadding),
                onClick = onAddButtonClick
            )
        }
    }
}

@Composable
fun FullWidthButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Orange,
            contentColor = Color.White,
            disabledBackgroundColor = Grey,
            disabledContentColor = Color.White
        ),
        onClick = onClick,
        shape = Shapes.small
    ) {
        BaseBigCenteredText(
            text = text,
            modifier = FullWidthTextModifier,
            textColor = Color.White
        )
    }
}

@Composable
fun FullWidthCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = FullWidthModifier.then(modifier),
        shape = Shapes.small,
        backgroundColor = Color.White,
        elevation = Dimens.Elevations.baseElevation,
        content = content
    )
}

@Composable
fun BaseLazyColumn(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(Dimens.Paddings.basePadding),
    content: LazyListScope.() -> Unit
) {
    LazyColumn(
        modifier = FullWidthModifier.then(modifier),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(Dimens.Paddings.basePadding, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = content
    )
}
