package io.github.tuguzt.flexibleproject.view.root.main.board

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.domain.model.*
import io.github.tuguzt.flexibleproject.view.theme.FlexibleProjectTheme
import kotlinx.datetime.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Board(
    board: Board,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Card(modifier = modifier, onClick = onClick) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = board.name,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.headlineSmall,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(
                    id = R.string.completed_tasks,
                    board.tasks.filter(Task::isCompleted).size,
                    board.tasks.size,
                ),
            )
        }
    }
}

@Preview
@Composable
private fun BoardPreview() {
    FlexibleProjectTheme {
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
        val board = Board(
            id = Id("Hello World"),
            name = "My fresh new board",
            description = "Some loooong description",
            tasks = tasks,
            owner = User(
                id = Id("Hello World"),
                name = "tuguzT",
                email = null,
                role = UserRole.Administrator,
            ),
        )
        Board(
            board = board,
            modifier = Modifier.fillMaxWidth(),
            onClick = {},
        )
    }
}
