package io.github.tuguzt.flexibleproject.view.screens.auth.signin

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.domain.model.user.UserCredentials
import io.github.tuguzt.flexibleproject.view.screens.NavGraphs
import io.github.tuguzt.flexibleproject.view.screens.auth.AuthContent
import io.github.tuguzt.flexibleproject.view.screens.auth.AuthNavGraph
import io.github.tuguzt.flexibleproject.view.screens.auth.PasswordTextField
import io.github.tuguzt.flexibleproject.view.screens.destinations.SignUpScreenDestination
import io.github.tuguzt.flexibleproject.view.utils.NameTextField
import io.github.tuguzt.flexibleproject.view.utils.collectInLaunchedEffectWithLifecycle
import io.github.tuguzt.flexibleproject.viewmodel.auth.SignInViewModel
import io.github.tuguzt.flexibleproject.viewmodel.auth.store.SignInStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.auth.store.SignInStore.Label

@AuthNavGraph(start = true)
@Destination
@Composable
fun SignInScreen(
    navigator: DestinationsNavigator,
    viewModel: SignInViewModel,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    val state by viewModel.stateFlow.collectAsStateWithLifecycle()
    viewModel.labels.collectInLaunchedEffectWithLifecycle block@{ label ->
        val message = when (label) {
            Label.LocalStoreError -> context.getString(R.string.local_store_error)
            Label.NetworkAccessError -> context.getString(R.string.network_access_error)
            Label.UnknownError -> context.getString(R.string.unknown_error)
            is Label.NoUserWithName -> context.getString(R.string.no_user_with_name, label.name)
        }
        snackBarHostState.showSnackbar(
            message = message,
            actionLabel = context.getString(R.string.dismiss),
        )
    }

    AuthContent(
        title = stringResource(R.string.welcome_back),
        submitText = stringResource(R.string.sign_in),
        onSubmit = {
            val credentials = UserCredentials(state.name, state.password)
            val intent = Intent.SignIn(credentials)
            viewModel.accept(intent)
        },
        submitEnabled = state.valid,
        loading = state.loading,
        changeAuthTypeText = stringResource(R.string.dont_have_an_account),
        changeAuthTypeClickableText = stringResource(R.string.sign_up),
        onChangeAuthType = {
            val direction = SignUpScreenDestination()
            navigator.navigate(direction) {
                popUpTo(NavGraphs.auth) { inclusive = true }
            }
        },
        snackBarHostState = snackBarHostState,
    ) {
        NameTextField(
            name = state.name,
            onNameChange = { name ->
                val intent = Intent.ChangeName(name)
                viewModel.accept(intent)
            },
            enabled = !state.loading,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(16.dp))
        PasswordTextField(
            password = state.password,
            onPasswordChange = { password ->
                val intent = Intent.ChangePassword(password)
                viewModel.accept(intent)
            },
            passwordVisible = state.passwordVisible,
            onPasswordVisibleChange = { passwordVisible ->
                val intent = Intent.ChangePasswordVisible(passwordVisible)
                viewModel.accept(intent)
            },
            enabled = !state.loading,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
