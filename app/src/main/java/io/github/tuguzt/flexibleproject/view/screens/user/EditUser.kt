package io.github.tuguzt.flexibleproject.view.screens.user

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.view.utils.collectInLaunchedEffectWithLifecycle
import io.github.tuguzt.flexibleproject.viewmodel.user.UpdateUserViewModel
import io.github.tuguzt.flexibleproject.viewmodel.user.store.UpdateUserStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.user.store.UpdateUserStore.Label

@RootNavGraph
@Destination
@Composable
fun EditUserScreen(
    navigator: DestinationsNavigator,
    viewModel: UpdateUserViewModel = hiltViewModel(),
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    val state by viewModel.stateFlow.collectAsStateWithLifecycle()
    viewModel.labels.collectInLaunchedEffectWithLifecycle block@{ label ->
        val message = when (label) {
            Label.NoCurrentUser -> return@block
            is Label.EmailAlreadyTaken -> context.getString(
                R.string.user_with_email_already_exists,
                label.email,
            )

            is Label.NameAlreadyTaken -> context.getString(
                R.string.user_with_name_already_exists,
                label.name,
            )

            Label.LocalStoreError -> context.getString(R.string.local_store_error)
            Label.NetworkAccessError -> context.getString(R.string.network_access_error)
            Label.UnknownError -> context.getString(R.string.unknown_error)
            Label.UserUpdated -> {
                navigator.navigateUp()
                return@block
            }
        }
        snackBarHostState.showSnackbar(
            message = message,
            actionLabel = context.getString(R.string.dismiss),
        )
    }

    EditUserContent(
        name = state.name,
        onNameChange = { name ->
            val intent = Intent.ChangeName(name)
            viewModel.accept(intent)
        },
        displayName = state.displayName,
        onDisplayNameChanged = { displayName ->
            val intent = Intent.ChangeDisplayName(displayName)
            viewModel.accept(intent)
        },
        email = state.email.orEmpty(),
        onEmailChange = { email ->
            val intent = Intent.ChangeEmail(email.takeIf(String::isNotBlank))
            viewModel.accept(intent)
        },
        avatar = state.avatar.orEmpty(),
        onAvatarChange = { avatar ->
            val intent = Intent.ChangeAvatar(avatar.takeIf(String::isNotBlank))
            viewModel.accept(intent)
        },
        loading = state.loading,
        valid = state.valid,
        onUpdateUserClick = {
            val intent = Intent.UpdateUser
            viewModel.accept(intent)
        },
        onNavigationClick = navigator::navigateUp,
        snackBarHostState = snackBarHostState,
    )
}
