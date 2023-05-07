package io.github.tuguzt.flexibleproject.view.screens.user

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import io.github.tuguzt.flexibleproject.domain.model.Id
import io.github.tuguzt.flexibleproject.domain.model.user.Role
import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.model.user.UserData
import io.github.tuguzt.flexibleproject.view.theme.AppTheme
import io.github.tuguzt.flexibleproject.view.utils.NavigateUpIconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserTopBar(
    user: User,
    onNavigationClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = user.data.displayName,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        navigationIcon = { NavigateUpIconButton(onClick = onNavigationClick) },
    )
    // TODO user avatar, scroll behaviour
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun UserTopBar() {
    val user = User(
        id = Id("1"),
        data = UserData(
            name = "tuguzT",
            displayName = "Timur Tugushev",
            role = Role.User,
            email = "timurka.tugushev@gmail.com",
            avatarUrl = null,
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
