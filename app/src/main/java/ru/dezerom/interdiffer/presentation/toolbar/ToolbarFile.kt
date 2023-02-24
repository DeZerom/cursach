package ru.dezerom.interdiffer.presentation.toolbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.dezerom.interdiffer.R
import ru.dezerom.interdiffer.presentation.utils.FullWidthModifier
import ru.dezerom.interdiffer.presentation.utils.res.Dimens
import ru.dezerom.interdiffer.presentation.widgets.BoldExtraBigText
import ru.dezerom.interdiffer.presentation.widgets.FullWidthCard
import ru.dezerom.interdiffer.ui.theme.bottomRoundedShape

@Composable
fun Toolbar(
    title: String,
    showBackButton: Boolean = false,
    onBackButtonClick: () -> Unit = {}
) {
    FullWidthCard(
        shape = bottomRoundedShape(),
        modifier = Modifier.height(Dimens.Sizes.toolbarHeight)
    ) {
        Box(
            modifier = FullWidthModifier
        ) {
            if (showBackButton) {
                Image(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = stringResource(R.string.back),
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
    }
}
