package io.github.tuguzt.flexibleproject.view.screens.auth.signup

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.domain.model.user.UserCredentials
import io.github.tuguzt.flexibleproject.view.screens.NavGraphs
import io.github.tuguzt.flexibleproject.view.screens.auth.AuthContent
import io.github.tuguzt.flexibleproject.view.screens.auth.AuthNavGraph
import io.github.tuguzt.flexibleproject.view.screens.destinations.SignInScreenDestination
import io.github.tuguzt.flexibleproject.view.utils.collectInLaunchedEffectWithLifecycle
import io.github.tuguzt.flexibleproject.viewmodel.auth.AuthViewModel
import io.github.tuguzt.flexibleproject.viewmodel.auth.store.AuthStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.auth.store.AuthStore.Label

@AuthNavGraph
@Destination
@Composable
fun SignUpScreen(
    navigator: DestinationsNavigator,
    viewModel: AuthViewModel,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    val state by viewModel.stateFlow.collectAsStateWithLifecycle()
    viewModel.labels.collectInLaunchedEffectWithLifecycle block@{ label ->
        val message = when (label) {
            is Label.NotFoundByName -> return@block
            Label.LocalStoreError -> context.getString(R.string.local_store_error)
            Label.NetworkAccessError -> context.getString(R.string.network_access_error)
            Label.UnknownError -> context.getString(R.string.unknown_error)
            is Label.NameAlreadyTaken -> context.getString(
                R.string.user_with_name_already_exists,
                label.name,
            )
        }
        snackBarHostState.showSnackbar(
            message = message,
            actionLabel = context.getString(R.string.dismiss),
        )
    }

    AuthContent(
        title = stringResource(R.string.create_new_account),
        name = state.name,
        onNameChange = { name ->
            val intent = Intent.ChangeName(name)
            viewModel.accept(intent)
        },
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
        submitText = stringResource(R.string.sign_up),
        onSubmit = {
            val credentials = UserCredentials(state.name, state.password)
            val intent = Intent.SignUp(credentials)
            viewModel.accept(intent)
        },
        submitEnabled = state.valid,
        loading = state.loading,
        changeAuthTypeText = stringResource(R.string.already_have_an_account),
        changeAuthTypeClickableText = stringResource(R.string.sign_in),
        onChangeAuthType = {
            val direction = SignInScreenDestination()
            navigator.navigate(direction) {
                popUpTo(NavGraphs.auth) { inclusive = true }
            }
        },
        snackBarHostState = snackBarHostState,
    )
}
