package io.github.tuguzt.flexibleproject.view.screen.main.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.view.navigation.MainScreenDestination.Home
import io.github.tuguzt.flexibleproject.view.screen.main.board.BoardList
import io.github.tuguzt.flexibleproject.view.theme.FlexibleProjectTheme
import io.github.tuguzt.flexibleproject.viewmodel.main.MainViewModel
import io.github.tuguzt.flexibleproject.viewmodel.main.home.HomeViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(
    @Suppress("UNUSED_PARAMETER") destination: Home = Home(LocalContext.current),
    mainViewModel: MainViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val appName = context.getString(R.string.app_name)

    LaunchedEffect(Unit) {
        mainViewModel.updateTitle(appName)
    }

    val swipeRefreshState = rememberSwipeRefreshState(homeViewModel.uiState.isRefreshing)
    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = homeViewModel::refreshBoards,
    ) {
        AnimatedContent(
            targetState = homeViewModel.uiState.boards.isNotEmpty(),
        ) { hasBoards ->
            if (hasBoards) {
                val boardListState = rememberLazyListState()
                BoardList(
                    boards = homeViewModel.uiState.boards,
                    modifier = Modifier.fillMaxSize(),
                    lazyListState = boardListState,
                    onBoardClick = { /* todo */ },
                )
            } else {
                NoBoardsBanner()
            }
        }
    }
}

@Composable
private fun NoBoardsBanner() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            modifier = Modifier.size(64.dp),
            imageVector = Icons.Rounded.Description,
            contentDescription = stringResource(R.string.no_boards),
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.no_boards),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall,
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.create_board_or_refresh),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.fillMaxWidth(fraction = 0.6f),
        )
    }
}

@Preview
@Composable
private fun NoBoardsBannerPreview() {
    FlexibleProjectTheme {
        Surface {
            NoBoardsBanner()
        }
    }
}
