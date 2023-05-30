package io.github.tuguzt.flexibleproject.view.screens.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Shield
import androidx.compose.material.icons.rounded.Tag
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.material.placeholder
import io.github.tuguzt.flexibleproject.domain.model.Id
import io.github.tuguzt.flexibleproject.domain.model.user.Email
import io.github.tuguzt.flexibleproject.domain.model.user.Name
import io.github.tuguzt.flexibleproject.domain.model.user.Role
import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.model.user.UserData
import io.github.tuguzt.flexibleproject.view.theme.AppTheme
import io.github.tuguzt.flexibleproject.view.utils.OneLineTitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreenContent(
    user: User?,
    onNavigationClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(),
) {
    Scaffold(
        topBar = {
            UserTopBar(
                user = user,
                onNavigationClick = onNavigationClick,
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
        ) {
            UserDataCard(
                user = user?.data,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun UserDataCard(
    user: UserData?,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        tonalElevation = 4.dp,
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            UserName(name = user?.name)
            Spacer(modifier = Modifier.height(16.dp))
            UserEmail(email = user?.email)
            if (user != null && user.role > Role.User) {
                Spacer(modifier = Modifier.height(16.dp))
                UserRole(role = user.role)
            }
        }
    }
}

@Composable
private fun UserDataRow(
    data: String?,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    iconDescription: String? = null,
    placeholder: String = "placeholder",
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(imageVector = icon, contentDescription = iconDescription)
        Spacer(modifier = Modifier.width(16.dp))
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
    UserDataRow(
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
    UserDataRow(
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
    UserDataRow(
        data = role.toString(),
        icon = Icons.Rounded.Shield,
        modifier = modifier,
        placeholder = Role.Moderator.toString(),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun UserScreenContentWithUser() {
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
        UserScreenContent(
            user = user,
            onNavigationClick = {},
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun UserScreenContentWithoutUser() {
    AppTheme {
        UserScreenContent(
            user = null,
            onNavigationClick = {},
        )
    }
}
