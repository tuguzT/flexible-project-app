package io.github.tuguzt.flexibleproject.view.screens.user

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Article
import androidx.compose.material.icons.rounded.Dashboard
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Groups3
import androidx.compose.material.icons.rounded.Shield
import androidx.compose.material.icons.rounded.Tag
import androidx.compose.material.icons.rounded.Task
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.material.placeholder
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.domain.model.Id
import io.github.tuguzt.flexibleproject.domain.model.user.Email
import io.github.tuguzt.flexibleproject.domain.model.user.Name
import io.github.tuguzt.flexibleproject.domain.model.user.Role
import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.model.user.UserData
import io.github.tuguzt.flexibleproject.view.theme.AppTheme
import io.github.tuguzt.flexibleproject.view.utils.OneLineTitle
import io.github.tuguzt.flexibleproject.view.utils.toTranslatedString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserContent(
    user: User?,
    loading: Boolean,
    onNavigationClick: () -> Unit,
    onWorkspacesClick: () -> Unit,
    onProjectsClick: () -> Unit,
    onTasksClick: () -> Unit,
    onMethodologiesClick: () -> Unit,
    topBarActions: @Composable RowScope.() -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(),
) {
    Scaffold(
        topBar = {
            UserTopBar(
                user = user,
                loading = loading,
                onNavigationClick = onNavigationClick,
                actions = topBarActions,
                scrollBehavior = scrollBehavior,
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            UserDataCard(
                user = user?.data,
                modifier = Modifier.fillMaxWidth(),
            )
            UserActions(
                loading = loading,
                onWorkspacesClick = onWorkspacesClick,
                onProjectsClick = onProjectsClick,
                onTasksClick = onTasksClick,
                onMethodologiesClick = onMethodologiesClick,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun UserActions(
    loading: Boolean,
    onWorkspacesClick: () -> Unit,
    onProjectsClick: () -> Unit,
    onTasksClick: () -> Unit,
    onMethodologiesClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        tonalElevation = 1.dp,
    ) {
        Column {
            WorkspacesAction(
                loading = loading,
                onClick = onWorkspacesClick,
                modifier = Modifier.fillMaxWidth(),
            )
            ProjectsAction(
                loading = loading,
                onClick = onProjectsClick,
                modifier = Modifier.fillMaxWidth(),
            )
            TasksAction(
                loading = loading,
                onClick = onTasksClick,
                modifier = Modifier.fillMaxWidth(),
            )
            MethodologiesAction(
                loading = loading,
                onClick = onMethodologiesClick,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun WorkspacesAction(
    loading: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.takeIf { loading } ?: modifier.clickable(onClick = onClick),
    ) {
        UserItemRow(
            data = stringResource(R.string.workspaces),
            icon = Icons.Rounded.Groups3,
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Composable
private fun ProjectsAction(
    loading: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.takeIf { loading } ?: modifier.clickable(onClick = onClick),
    ) {
        UserItemRow(
            data = stringResource(R.string.projects),
            icon = Icons.Rounded.Dashboard,
            modifier = modifier.padding(16.dp),
        )
    }
}

@Composable
private fun TasksAction(
    loading: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.takeIf { loading } ?: modifier.clickable(onClick = onClick),
    ) {
        UserItemRow(
            data = stringResource(R.string.tasks),
            icon = Icons.Rounded.Task,
            modifier = modifier.padding(16.dp),
        )
    }
}

@Composable
private fun MethodologiesAction(
    loading: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.takeIf { loading } ?: modifier.clickable(onClick = onClick),
    ) {
        UserItemRow(
            data = stringResource(R.string.methodologies),
            icon = Icons.Rounded.Article,
            modifier = modifier.padding(16.dp),
        )
    }
}

@Composable
private fun UserDataCard(
    user: UserData?,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        tonalElevation = 1.dp,
    ) {
        Column {
            UserName(
                name = user?.name,
                modifier = Modifier.padding(16.dp),
            )
            UserEmail(
                email = user?.email,
                modifier = Modifier.padding(16.dp),
            )
            if (user != null && user.role > Role.User) {
                UserRole(
                    role = user.role,
                    modifier = Modifier.padding(16.dp),
                )
            }
        }
    }
}

@Composable
private fun UserItemRow(
    data: String?,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    iconDescription: String? = null,
    placeholder: String = "placeholder",
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Icon(imageVector = icon, contentDescription = iconDescription)
        OneLineTitle(
            text = data ?: placeholder,
            modifier = Modifier.placeholder(visible = data == null),
        )
    }
}

@Composable
private fun UserName(
    name: Name?,
    modifier: Modifier = Modifier,
) {
    UserItemRow(
        data = name,
        icon = Icons.Rounded.Tag,
        modifier = modifier,
    )
}

@Composable
private fun UserEmail(
    email: Email?,
    modifier: Modifier = Modifier,
) {
    UserItemRow(
        data = email,
        icon = Icons.Rounded.Email,
        modifier = modifier,
        placeholder = "example@mail.com",
    )
}

@Composable
private fun UserRole(
    role: Role,
    modifier: Modifier = Modifier,
) {
    UserItemRow(
        data = role.toTranslatedString(),
        icon = Icons.Rounded.Shield,
        modifier = modifier,
        placeholder = Role.Moderator.toTranslatedString(),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun UserContentWithUser() {
    val user = User(
        id = Id("1"),
        data = UserData(
            name = "tuguzT",
            displayName = "Timur Tugushev",
            role = Role.Administrator,
            email = "timurka.tugushev@gmail.com",
            avatar = "https://avatars.githubusercontent.com/u/56771526",
        ),
    )

    AppTheme {
        UserContent(
            user = user,
            loading = false,
            onWorkspacesClick = {},
            onProjectsClick = {},
            onTasksClick = {},
            onMethodologiesClick = {},
            onNavigationClick = {},
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun UserContentWithoutUser() {
    AppTheme {
        UserContent(
            user = null,
            loading = false,
            onWorkspacesClick = {},
            onProjectsClick = {},
            onTasksClick = {},
            onMethodologiesClick = {},
            onNavigationClick = {},
        )
    }
}
