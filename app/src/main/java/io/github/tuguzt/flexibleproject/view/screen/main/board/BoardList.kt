package io.github.tuguzt.flexibleproject.view.screen.main.board

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.tuguzt.flexibleproject.domain.model.*
import io.github.tuguzt.flexibleproject.view.theme.FlexibleProjectTheme
import kotlinx.datetime.LocalDateTime

@Composable
fun BoardList(
    boards: List<Board>,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState(),
    onBoardClick: (Board) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        state = lazyListState,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(boards, key = { board -> board.id.toString() }) { board ->
            Board(
                board = board,
                modifier = Modifier.fillMaxWidth(),
                onClick = { onBoardClick(board) },
            )
        }
    }
}

@Preview
@Composable
private fun BoardListPreview() {
    FlexibleProjectTheme {
        val user = User(
            id = Id("Hello World"),
            name = "tuguzT",
            email = null,
            role = UserRole.Administrator,
        )
        val tasks = List(3) {
            Task(
                id = Id(it.toString()),
                name = "Lorem Ipsum",
                description = """
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, 
                    sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. 
                    Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris 
                    nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in 
                    reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla 
                    pariatur. Excepteur sint occaecat cupidatat non proident, sunt in 
                    culpa qui officia deserunt mollit anim id est laborum.
                """.trimIndent(),
                labels = listOf(
                    Label(name = "Kotlin", color = LabelColor.Blue),
                    Label(name = "MDev", color = LabelColor.Green),
                    Label(name = "Jetpack Compose", color = LabelColor.Red),
                ),
                deadline = LocalDateTime(
                    year = 2022,
                    monthNumber = 10,
                    dayOfMonth = 20,
                    hour = 0,
                    minute = 0,
                ),
                isCompleted = false,
            )
        }
        val boards = List(3) {
            Board(
                id = Id(it.toString()),
                name = "My fresh new board",
                description = "Some loooong description",
                tasks = tasks,
                owner = user,
            )
        }
        BoardList(
            boards = boards,
            modifier = Modifier.fillMaxSize(),
            onBoardClick = {},
        )
    }
}
