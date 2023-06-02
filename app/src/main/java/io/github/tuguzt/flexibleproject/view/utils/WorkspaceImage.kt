package io.github.tuguzt.flexibleproject.view.utils

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
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
    crossfade: Boolean = true,
    colorFilter: ColorFilter? = null,
    error: @Composable (() -> Unit)? = null,
) {
    val model = ImageRequest.Builder(LocalContext.current)
        .data(workspace?.data?.image)
        .crossfade(crossfade)
        .build()
    var isLoading by remember { mutableStateOf(true) }

    SubcomposeAsyncImage(
        model = model,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = modifier.placeholder(visible = isLoading || workspace == null),
        colorFilter = colorFilter,
        error = error?.let { { it() } },
        onLoading = { isLoading = true },
        onSuccess = { isLoading = false },
        onError = { isLoading = false },
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
            image = null,
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
