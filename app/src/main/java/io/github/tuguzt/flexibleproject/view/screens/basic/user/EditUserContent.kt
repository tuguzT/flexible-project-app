package io.github.tuguzt.flexibleproject.view.screens.basic.user

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.view.theme.AppTheme
import io.github.tuguzt.flexibleproject.view.utils.NameTextField

@Composable
fun EditUserContent(
    name: String,
    onNameChange: (String) -> Unit,
    displayName: String,
    onDisplayNameChanged: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    avatar: String,
    onAvatarChange: (String) -> Unit,
    loading: Boolean,
    valid: Boolean,
    onUpdateUserClick: () -> Unit,
    onNavigationClick: () -> Unit,
    focusManager: FocusManager = LocalFocusManager.current,
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    Scaffold(
        topBar = {
            EditUserTopBar(
                loading = loading,
                valid = valid,
                onUpdateUserClick = onUpdateUserClick,
                onNavigationClick = onNavigationClick,
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = focusManager::clearFocus,
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            NameTextField(
                name = name,
                onNameChange = onNameChange,
                modifier = Modifier.fillMaxWidth(),
                enabled = !loading,
                label = stringResource(R.string.update_name),
            )
            DisplayNameTextField(
                displayName = displayName,
                onDisplayNameChange = onDisplayNameChanged,
                modifier = Modifier.fillMaxWidth(),
                enabled = !loading,
                label = stringResource(R.string.update_display_name),
            )
            EmailTextField(
                email = email,
                onEmailChange = onEmailChange,
                modifier = Modifier.fillMaxWidth(),
                enabled = !loading,
                label = stringResource(R.string.update_email),
            )
        }
    }
}

@Preview
@Composable
private fun EditUserContent() {
    AppTheme {
        Surface {
            EditUserContent(
                name = "tuguzT",
                onNameChange = {},
                displayName = "Timur Tugushev",
                onDisplayNameChanged = {},
                email = "timurka.tugushev@gmail.com",
                onEmailChange = {},
                avatar = "",
                onAvatarChange = {},
                loading = false,
                valid = false,
                onUpdateUserClick = {},
                onNavigationClick = {},
            )
        }
    }
}
