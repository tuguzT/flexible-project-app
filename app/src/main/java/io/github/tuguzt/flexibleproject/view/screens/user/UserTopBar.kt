package io.github.tuguzt.flexibleproject.view.screens.user

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.material.placeholder
import io.github.tuguzt.flexibleproject.domain.model.Id
import io.github.tuguzt.flexibleproject.domain.model.user.Role
import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.model.user.UserData
import io.github.tuguzt.flexibleproject.view.theme.AppTheme
import io.github.tuguzt.flexibleproject.view.utils.NavigateUpIconButton
import io.github.tuguzt.flexibleproject.view.utils.OneLineTitle
import io.github.tuguzt.flexibleproject.view.utils.UserAvatar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserTopBar(
    user: User?,
    onNavigationClick: () -> Unit,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    val collapsedFraction by remember {
        derivedStateOf { scrollBehavior?.state?.collapsedFraction ?: 0.0f }
    }
    val surfaceColor = MaterialTheme.colorScheme.surface
    val containerColor by remember {
        derivedStateOf { surfaceColor.copy(alpha = collapsedFraction) }
    }

    Box {
        UserAvatar(
            user = user,
            modifier = Modifier.matchParentSize(),
        )
        LargeTopAppBar(
            modifier = modifier,
            title = { UserTitle(user, collapsedFraction) },
            navigationIcon = { NavigateUpIconButton(onClick = onNavigationClick) },
            scrollBehavior = scrollBehavior,
            colors = TopAppBarDefaults.largeTopAppBarColors(containerColor),
        )
    }
}

@Composable
private fun UserTitle(
    user: User?,
    collapsedFraction: Float,
) {
    Row(
        modifier = Modifier.alpha(LocalContentColor.current.alpha),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (collapsedFraction > 0.8) {
            UserAvatar(
                user = user,
                modifier = Modifier.size(42.dp).clip(CircleShape),
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        OneLineTitle(
            text = user?.data?.displayName ?: "Placeholder",
            modifier = Modifier.placeholder(visible = user == null),
        )
    }
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
        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

        Scaffold(
            topBar = {
                UserTopBar(
                    user = user,
                    onNavigationClick = {},
                    scrollBehavior = scrollBehavior,
                )
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        ) { padding ->
            Box(
                modifier = Modifier
                    .padding(padding)
                    .verticalScroll(rememberScrollState()),
            )
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
