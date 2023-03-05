package ru.dezerom.interdiffer.presentation.sreens.comparisons

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import ru.dezerom.interdiffer.R
import ru.dezerom.interdiffer.presentation.sreens.base.BaseScreen
import ru.dezerom.interdiffer.presentation.toolbar.Toolbar

@Composable
fun ComparisonsScreen(
    viewModel: ComparisonsViewModel,
    navController: NavController
) {
    val state = viewModel.state.collectAsState()

    BaseScreen(
        viewModel = viewModel,
        navController = navController,
        toolbar = {
            Toolbar(title = stringResource(id = R.string.comparisons))
        }
    ) {
        when (val st = state.value) {
            is ComparisonsScreenState.ShowList -> {

            }

            ComparisonsScreenState.Empty -> {}
        }
    }
}

@Composable
private fun ShowList() {} //todo