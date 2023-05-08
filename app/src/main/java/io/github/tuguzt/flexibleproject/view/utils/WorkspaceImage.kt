package io.github.tuguzt.flexibleproject.view.utils

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Autorenew
import androidx.compose.material.icons.rounded.Groups3
import androidx.compose.material.icons.rounded.SignalWifiConnectedNoInternet4
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.placeholder.material.placeholder
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.domain.model.Id
import io.github.tuguzt.flexibleproject.domain.model.workspace.Visibility
import io.github.tuguzt.flexibleproject.domain.model.workspace.Workspace
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceData
import io.github.tuguzt.flexibleproject.view.theme.AppTheme

@Composable
fun WorkspaceImage(
    workspace: Workspace?,
    modifier: Modifier = Modifier,
    contentDescription: String? = stringResource(R.string.workspace_image),
) {
    AsyncImage(
        model = workspace?.data?.imageUrl,
        placeholder = rememberVectorPainter(Icons.Rounded.Autorenew),
        error = rememberVectorPainter(Icons.Rounded.SignalWifiConnectedNoInternet4),
        fallback = rememberVectorPainter(Icons.Rounded.Groups3),
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = modifier.placeholder(visible = workspace == null),
    )
}

@Preview
@Composable
private fun WorkspaceImageWithWorkspace() {
    val workspace = Workspace(
        id = Id("workspace"),
        data = WorkspaceData(
            name = "Workspace",
            description = "Sample workspace",
            visibility = Visibility.Public,
            imageUrl = null,
        ),
    )

    AppTheme {
        WorkspaceImage(
            workspace = workspace,
            modifier = Modifier.size(72.dp),
        )
    }
}

@Preview
@Composable
private fun WorkspaceImageWithoutWorkspace() {
    AppTheme {
        WorkspaceImage(
            workspace = null,
            modifier = Modifier.size(72.dp),
        )
    }
}
