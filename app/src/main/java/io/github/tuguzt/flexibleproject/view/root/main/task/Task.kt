package io.github.tuguzt.flexibleproject.view.root.main.task

import android.text.format.DateFormat
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckBox
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.tuguzt.flexibleproject.domain.model.Task
import io.github.tuguzt.flexibleproject.view.theme.FlexibleProjectTheme
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.domain.model.Id
import io.github.tuguzt.flexibleproject.domain.model.Label
import io.github.tuguzt.flexibleproject.domain.model.LabelColor
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.UtcOffset
import kotlinx.datetime.toInstant
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Task(
    task: Task,
    modifier: Modifier = Modifier,
    onDeadlineClick: () -> Unit,
) {
    val context = LocalContext.current
    val dateFormat = DateFormat.getMediumDateFormat(context)

    Card(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 8.dp),
        ) {
            Text(
                text = task.name,
                style = MaterialTheme.typography.headlineSmall,
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(task.labels) { label ->
                    val color = Color(label.color.argb)
                    SuggestionChip(
                        onClick = {},
                        label = { Text(text = label.name) },
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            containerColor = color,
                            labelColor = contentColorFor(color),
                        ),
                    )
                }
            }
            AssistChip(
                onClick = onDeadlineClick,
                label = {
                    val millis = task.deadline.toInstant(UtcOffset.ZERO).toEpochMilliseconds()
                    Text(text = dateFormat.format(millis))
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.CheckBox,
                        contentDescription = stringResource(R.string.deadline),
                        modifier = Modifier.size(AssistChipDefaults.IconSize),
                    )
                },
            )
        }
    }
}

@Preview
@Composable
private fun TaskPreview() {
    FlexibleProjectTheme {
        val task = Task(
            id = Id(UUID.randomUUID().toString()),
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
        Task(
            task = task,
            modifier = Modifier.fillMaxWidth(),
            onDeadlineClick = {},
        )
    }
}
