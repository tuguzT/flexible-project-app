package io.github.tuguzt.flexibleproject.view.screen.auth

import android.content.res.Configuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.view.theme.FlexibleProjectTheme
import io.github.tuguzt.flexibleproject.viewmodel.auth.AuthViewModel

@Composable
fun SignUpScreen(
    onSignUp: (AuthVariant) -> Unit,
    onSignInNavigate: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
) = AuthScreen(snackbarHostState) {
    AuthScreenContent(
        title = stringResource(R.string.sign_up),
        viewModel = viewModel,
        onAuth = onSignUp,
        alternativeDestinationDescription = stringResource(R.string.have_account),
        alternativeDestinationText = stringResource(R.string.sign_in),
        onAlternativeNavigate = onSignInNavigate,
    )
}

@Preview(name = "Light Mode")
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun SignUpScreenPreview() {
    FlexibleProjectTheme {
        SignUpScreen(onSignUp = {}, onSignInNavigate = {})
    }
}
