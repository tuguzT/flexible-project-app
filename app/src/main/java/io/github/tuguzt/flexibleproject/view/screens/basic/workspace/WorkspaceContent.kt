package io.github.tuguzt.flexibleproject.view.screens.basic.workspace

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.material.placeholder
import io.github.tuguzt.flexibleproject.domain.model.project.Project
import io.github.tuguzt.flexibleproject.domain.model.workspace.Visibility
import io.github.tuguzt.flexibleproject.domain.model.workspace.Workspace
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceData
import io.github.tuguzt.flexibleproject.domain.model.workspace.WorkspaceId
import io.github.tuguzt.flexibleproject.view.screens.basic.project.ProjectCard
import io.github.tuguzt.flexibleproject.view.theme.AppTheme
import io.github.tuguzt.flexibleproject.view.utils.toTranslatedString
import io.github.tuguzt.flexibleproject.viewmodel.basic.workspace.store.WorkspaceStore.State.WorkspaceWithProjects

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkspaceContent(
    workspace: WorkspaceWithProjects?,
    loading: Boolean,
    onNavigationClick: () -> Unit,
    onProjectClick: (Project) -> Unit,
    projectImage: @Composable (Project) -> Unit,
    topBarActions: @Composable RowScope.() -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(),
) {
    Scaffold(
        topBar = {
            WorkspaceTopBar(
                workspace = workspace?.workspace,
                loading = loading,
                onNavigationClick = onNavigationClick,
                actions = topBarActions,
                scrollBehavior = scrollBehavior,
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                WorkspaceDataCard(
                    workspace = workspace?.workspace,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            items(
                items = workspace?.projects.orEmpty(),
                key = { project -> project.id.toString() },
            ) { project ->
                ProjectCard(
                    project = project,
                    image = { projectImage(project) },
                    onClick = { onProjectClick(project) },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                )
            }
        }
    }
}

@Composable
private fun WorkspaceDataCard(
    workspace: Workspace?,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        tonalElevation = 1.dp,
    ) {
        Column {
            WorkspaceDescription(
                description = workspace?.data?.description,
                modifier = Modifier.padding(16.dp),
            )
            WorkspaceVisibility(
                visibility = workspace?.data?.visibility,
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
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Icon(imageVector = icon, contentDescription = iconDescription)
        Text(
            text = data ?: placeholder,
            modifier = Modifier.placeholder(visible = data == null),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun WorkspaceContentWithWorkspace() {
    val workspace = WorkspaceWithProjects(
        workspace = Workspace(
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
        ),
        projects = listOf(),
    )
    AppTheme {
        WorkspaceContent(
            workspace = workspace,
            loading = false,
            onNavigationClick = {},
            onProjectClick = {},
            projectImage = {},
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun WorkspaceContentWithoutWorkspace() {
    AppTheme {
        WorkspaceContent(
            workspace = null,
            loading = false,
            onNavigationClick = {},
            onProjectClick = {},
            projectImage = {},
        )
    }
}
