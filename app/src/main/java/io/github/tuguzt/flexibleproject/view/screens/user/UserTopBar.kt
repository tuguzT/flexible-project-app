package io.github.tuguzt.flexibleproject.view.screens.user

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.placeholder.material.placeholder
import io.github.tuguzt.flexibleproject.domain.model.Id
import io.github.tuguzt.flexibleproject.domain.model.user.Role
import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.model.user.UserData
import io.github.tuguzt.flexibleproject.view.theme.AppTheme
import io.github.tuguzt.flexibleproject.view.utils.NavigateUpIconButton
import io.github.tuguzt.flexibleproject.view.utils.OneLineTitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserTopBar(
    user: User?,
    onNavigationClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier,
        title = { UserTitle(user = user) },
        navigationIcon = { NavigateUpIconButton(onClick = onNavigationClick) },
    )
    // TODO user avatar, scroll behaviour
}

@Composable
private fun UserTitle(user: User?) {
    OneLineTitle(
        text = user?.data?.displayName ?: "Placeholder",
        modifier = Modifier.placeholder(visible = user == null),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun UserTopBarWithUser() {
    val user = User(
        id = Id("1"),
        data = UserData(
            name = "tuguzT",
            displayName = "Timur Tugushev",
            role = Role.User,
            email = "timurka.tugushev@gmail.com",
            avatarUrl = "https://avatars.githubusercontent.com/u/56771526",
        ),
    )

    AppTheme {
        Scaffold(
            topBar = {
                UserTopBar(user = user, onNavigationClick = {})
            },
        ) { padding ->
            Box(modifier = Modifier.padding(padding))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun UserTopBarWithoutUser() {
    AppTheme {
        Scaffold(
            topBar = {
                UserTopBar(user = null, onNavigationClick = {})
            },
        ) { padding ->
            Box(modifier = Modifier.padding(padding))
        }
    }
}
