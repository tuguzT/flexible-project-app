package io.github.tuguzt.flexibleproject.view.screens.workspace

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.material.placeholder
import io.github.tuguzt.flexibleproject.domain.model.workspace.Visibility
import io.github.tuguzt.flexibleproject.domain.model.workspace.Workspace
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceData
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceId
import io.github.tuguzt.flexibleproject.view.theme.AppTheme
import io.github.tuguzt.flexibleproject.view.utils.toTranslatedString

@Composable
fun WorkspaceContent(
    workspace: Workspace?,
    loading: Boolean,
    onNavigationClick: () -> Unit,
    topBarActions: @Composable RowScope.() -> Unit = {},
) {
    Scaffold(
        topBar = {
            WorkspaceTopBar(
                workspace = workspace,
                loading = loading,
                onNavigationClick = onNavigationClick,
                actions = topBarActions,
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            WorkspaceDataCard(
                data = workspace?.data,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun WorkspaceDataCard(
    data: WorkspaceData?,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        tonalElevation = 1.dp,
    ) {
        Column {
            WorkspaceDescription(
                description = data?.description,
                modifier = Modifier.padding(16.dp),
            )
            WorkspaceVisibility(
                visibility = data?.visibility,
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}

@Composable
private fun WorkspaceDescription(
    description: String?,
    modifier: Modifier = Modifier,
) {
    WorkspaceItemRow(
        data = description,
        icon = Icons.Rounded.Description,
        modifier = modifier,
    )
}

@Composable
private fun WorkspaceVisibility(
    visibility: Visibility?,
    modifier: Modifier = Modifier,
) {
    WorkspaceItemRow(
        data = visibility?.toTranslatedString(),
        icon = Icons.Rounded.Visibility,
        modifier = modifier,
        placeholder = Visibility.Public.toTranslatedString(),
    )
}

@Composable
private fun WorkspaceItemRow(
    data: String?,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    iconDescription: String? = null,
    placeholder: String = "placeholder",
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top,
    ) {
        Icon(imageVector = icon, contentDescription = iconDescription)
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = data ?: placeholder,
            modifier = Modifier.placeholder(visible = data == null),
        )
    }
}

@Preview
@Composable
private fun WorkspaceContentWithWorkspace() {
    val workspace = Workspace(
        id = WorkspaceId("workspace"),
        data = WorkspaceData(
            name = "Some workspace",
            description = """
                Very long description
                It is so long because I need to test the screen content
            """.trimIndent(),
            visibility = Visibility.Public,
            image = null,
        ),
    )
    AppTheme {
        WorkspaceContent(
            workspace = workspace,
            loading = false,
            onNavigationClick = {},
        )
    }
}

@Preview
@Composable
private fun WorkspaceContentWithoutWorkspace() {
    AppTheme {
        WorkspaceContent(
            workspace = null,
            loading = false,
            onNavigationClick = {},
        )
    }
}
