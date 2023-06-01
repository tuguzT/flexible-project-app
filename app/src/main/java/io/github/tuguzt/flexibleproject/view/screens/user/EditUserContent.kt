package io.github.tuguzt.flexibleproject.view.screens.user

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.view.utils.OneLineTitle

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

@Composable
private fun UpdateNameTextField(
    name: String,
    onNameChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    OutlinedTextField(
        value = name,
        onValueChange = onNameChange,
        modifier = modifier,
        enabled = enabled,
        label = { OneLineTitle(text = stringResource(R.string.update_name)) },
        singleLine = true,
    )
}

@Composable
private fun UpdateDisplayNameTextField(
    displayName: String,
    onDisplayNameChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    OutlinedTextField(
        value = displayName,
        onValueChange = onDisplayNameChange,
        modifier = modifier,
        enabled = enabled,
        label = { OneLineTitle(text = stringResource(R.string.update_display_name)) },
        singleLine = true,
    )
}

@Composable
private fun UpdateEmailTextField(
    email: String,
    onEmailChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        modifier = modifier,
        enabled = enabled,
        label = { OneLineTitle(text = stringResource(R.string.update_email)) },
        singleLine = true,
    )
}
