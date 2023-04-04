package ru.dezerom.interdiffer.presentation.sreens.comparison_detail.pager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import ru.dezerom.interdiffer.R
import ru.dezerom.interdiffer.domain.models.enums.ComparisonPagerScreens
import ru.dezerom.interdiffer.presentation.sreens.base.BaseScreen
import ru.dezerom.interdiffer.presentation.sreens.comparison_detail.pages.MatchesInfoScreen
import ru.dezerom.interdiffer.presentation.sreens.comparison_detail.pages.OverallInfoScreen
import ru.dezerom.interdiffer.presentation.toolbar.PagerToolbar

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ComparisonDetailScreen(
    viewModel: ComparisonDetailViewModel,
    navController: NavController
) {
    val pagerState = rememberPagerState()

    val state = viewModel.state.collectAsState()

    BaseScreen(
        viewModel = viewModel,
        navController = navController,
        toolbar = {
            PagerToolbar(
                title = stringResource(id = R.string.comparison),
                showBackButton = true,
                onBackButtonClick = remember(navController) { { navController.popBackStack() } },
                pagesTitles = remember {
                    ComparisonPagerScreens.values().map { it.toStrRes() }
                },
                pagerState = pagerState
            )
        }
    ) {
        when (val st = state.value) {
            is ComparisonDetailScreenState.ShowInfo -> {
                PagerComponent(state = st, pagerState = pagerState, viewModel = viewModel)
            }

            ComparisonDetailScreenState.Empty -> {}
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PagerComponent(
    viewModel: ComparisonDetailViewModel,
    state: ComparisonDetailScreenState.ShowInfo,
    pagerState: PagerState
) {

    val screens = remember { ComparisonPagerScreens.values() }

    HorizontalPager(
        pageCount = screens.size,
        modifier = Modifier.fillMaxSize(),
        state = pagerState
    ) {
        when (screens[it]) {
            ComparisonPagerScreens.OVERALL_INFO -> OverallInfoScreen(
                viewModel = viewModel,
                detailedComparison = state.detailedComparison
            )
            ComparisonPagerScreens.MATCHES_INFO -> MatchesInfoScreen(
                detailedComparison = state.detailedComparison
            )
        }
    }

}