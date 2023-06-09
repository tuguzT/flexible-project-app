package io.github.tuguzt.flexibleproject.view.screens.user

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.tuguzt.flexibleproject.view.theme.AppTheme

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
        ) {
            UpdateNameTextField(
                name = name,
                onNameChange = onNameChange,
                modifier = Modifier.fillMaxWidth(),
                enabled = !loading,
            )
            Spacer(modifier = Modifier.height(16.dp))
            UpdateDisplayNameTextField(
                displayName = displayName,
                onDisplayNameChange = onDisplayNameChanged,
                modifier = Modifier.fillMaxWidth(),
                enabled = !loading,
            )
            Spacer(modifier = Modifier.height(16.dp))
            UpdateEmailTextField(
                email = email,
                onEmailChange = onEmailChange,
                modifier = Modifier.fillMaxWidth(),
                enabled = !loading,
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
