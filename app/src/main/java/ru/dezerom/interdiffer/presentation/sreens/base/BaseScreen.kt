package ru.dezerom.interdiffer.presentation.sreens.base

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.dezerom.interdiffer.R
import ru.dezerom.interdiffer.presentation.utils.Dimens
import ru.dezerom.interdiffer.presentation.utils.FullWidthCardModifier
import ru.dezerom.interdiffer.presentation.utils.FullWidthModifier
import ru.dezerom.interdiffer.presentation.utils.FullWidthTextModifier
import ru.dezerom.interdiffer.presentation.widgets.BaseBigCenteredText
import ru.dezerom.interdiffer.presentation.widgets.BaseColumnWidget
import ru.dezerom.interdiffer.presentation.widgets.FullWidthButton
import ru.dezerom.interdiffer.presentation.widgets.HeadingCenteredText
import ru.dezerom.interdiffer.ui.theme.Orange
import ru.dezerom.interdiffer.ui.theme.Shapes

@Composable
fun BaseScreen(viewModel: BaseViewModel, content: @Composable () -> Unit) {
    val sideEffect = viewModel.baseSideEffect.collectAsState(initial = null)
    val state = viewModel.baseScreenState.collectAsState()

    when (val se = sideEffect.value) {
        is BaseSideEffect.ShowToast -> {
            val context = LocalContext.current
            Toast.makeText(context, se.text, Toast.LENGTH_LONG).show()
        }
        null -> {}
    }

    when (val st = state.value) {
        is BaseScreenState.Loading -> BaseColumnWidget { LoadingState() }
        is BaseScreenState.ShowingError -> BaseColumnWidget {
            ErrorState(st, viewModel::onCriticalErrorClick)
        }
        is BaseScreenState.ShowingInfo ->  content()
    }
}

@Composable
private fun LoadingState() {
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
            CircularProgressIndicator(
                color = Orange
            )

            HeadingCenteredText(
                text = stringResource(id = R.string.loading),
                modifier = FullWidthTextModifier
                    .padding(top = Dimens.Paddings.doublePadding)
            )

            BaseBigCenteredText(
                text = stringResource(id = R.string.loading_message),
                modifier = FullWidthTextModifier
                    .padding(top = Dimens.Paddings.largePadding)
            )
        }
    }
}

@Composable
private fun ErrorState(
    state: BaseScreenState.ShowingError,
    onCriticalButtonClick: () -> Unit
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
                painter = painterResource(id = R.drawable.ic_error),
                contentDescription = ""
            )

            HeadingCenteredText(
                text = stringResource(id = state.title),
                modifier = FullWidthTextModifier
                    .padding(top = Dimens.Paddings.doublePadding)
            )

            BaseBigCenteredText(
                text = stringResource(id = state.message),
                modifier = FullWidthTextModifier
                    .padding(top = Dimens.Paddings.largePadding)
            )

            FullWidthButton(
                text = stringResource(id = R.string.try_again),
                modifier = FullWidthModifier
                    .padding(top = Dimens.Paddings.doublePadding),
                onClick = onCriticalButtonClick
            )
        }
    }
}
