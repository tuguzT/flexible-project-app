package io.github.tuguzt.flexibleproject.view.screens.workspace

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun EditWorkspaceContent(
    onNavigationClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            EditWorkspaceTopBar(
                loading = false, // TODO
                valid = true, // TODO
                onSubmit = { /* TODO */ },
                onNavigationClick = onNavigationClick,
            )
        },
    ) { padding ->
        // TODO some content
        Box(modifier = Modifier.padding(padding))
    }
}
