package io.github.tuguzt.flexibleproject.view.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.ramcosta.composedestinations.annotation.Destination
import io.github.tuguzt.flexibleproject.R

@Destination
@Composable
fun SettingsScreen() {
    Text(text = stringResource(R.string.settings))
}
