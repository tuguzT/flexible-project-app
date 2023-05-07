package io.github.tuguzt.flexibleproject.view.screens.about

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import io.github.tuguzt.flexibleproject.R

@RootNavGraph
@Destination
@Composable
fun AboutScreen() {
    Text(text = stringResource(R.string.about))
}
