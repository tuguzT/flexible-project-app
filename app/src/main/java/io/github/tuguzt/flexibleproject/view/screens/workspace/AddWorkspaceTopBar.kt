package io.github.tuguzt.flexibleproject.view.screens.workspace

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.view.utils.CloseIconButton
import io.github.tuguzt.flexibleproject.view.utils.OneLineTitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWorkspaceTopBar(
    loading: Boolean,
    workspaceValid: Boolean,
    onAddWorkspaceClick: () -> Unit,
    onNavigationClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box {
        TopAppBar(
            modifier = modifier,
            title = { OneLineTitle(text = stringResource(R.string.add_new_workspace)) },
            navigationIcon = { CloseIconButton(onClick = onNavigationClick) },
            actions = {
                TextButton(
                    onClick = onAddWorkspaceClick,
                    enabled = workspaceValid && !loading,
                ) {
                    OneLineTitle(text = stringResource(R.string.add))
                }
            },
        )
        AnimatedVisibility(
            visible = loading,
            modifier = Modifier.align(Alignment.BottomCenter),
        ) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
    }
}
