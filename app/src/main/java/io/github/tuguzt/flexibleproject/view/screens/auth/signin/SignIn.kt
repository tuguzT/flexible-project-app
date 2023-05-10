package io.github.tuguzt.flexibleproject.view.screens.auth.signin

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.ramcosta.composedestinations.annotation.Destination
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.view.screens.auth.AuthNavGraph

@AuthNavGraph(start = true)
@Destination
@Composable
fun SignInScreen() {
    Text(text = stringResource(R.string.sign_in))
}