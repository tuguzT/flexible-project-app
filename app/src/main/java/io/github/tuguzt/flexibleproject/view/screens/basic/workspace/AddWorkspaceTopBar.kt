package io.github.tuguzt.flexibleproject.view.screens.basic.workspace

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.view.theme.AppTheme
import io.github.tuguzt.flexibleproject.view.utils.CloseIconButton
import io.github.tuguzt.flexibleproject.view.utils.OneLineTitle
import io.github.tuguzt.flexibleproject.view.utils.TextButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWorkspaceTopBar(
    loading: Boolean,
    valid: Boolean,
    onAddWorkspaceClick: () -> Unit,
    onNavigationClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box {
        TopAppBar(
            modifier = modifier,
            title = { OneLineTitle(text = stringResource(R.string.add_workspace)) },
            navigationIcon = { CloseIconButton(onClick = onNavigationClick, enabled = !loading) },
            actions = {
                TextButton(
                    text = stringResource(R.string.add),
                    onClick = onAddWorkspaceClick,
                    enabled = valid && !loading,
                )
            },
        )
        AnimatedVisibility(
            visible = loading,
            modifier = Modifier.align(Alignment.BottomCenter),
        ) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                strokeCap = StrokeCap.Round,
            )
        }
    }
}

@Preview
@Composable
private fun AddWorkspaceTopBar() {
    AppTheme {
        AddWorkspaceTopBar(
            loading = false,
            valid = true,
            onAddWorkspaceClick = {},
            onNavigationClick = {},
        )
    }
}
