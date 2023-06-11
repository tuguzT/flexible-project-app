package io.github.tuguzt.flexibleproject.view.screens.basic.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.tuguzt.flexibleproject.domain.model.project.Project
import io.github.tuguzt.flexibleproject.domain.model.workspace.Workspace
import io.github.tuguzt.flexibleproject.view.utils.ExpandedIcon
import io.github.tuguzt.flexibleproject.view.utils.OneLineTitle
import io.github.tuguzt.flexibleproject.viewmodel.basic.home.store.HomeStore.State.WorkspaceWithProjects

@Composable
fun HomeWorkspaces(
    content: HomeContent.WorkspacesContent,
    onWorkspaceClick: (Workspace) -> Unit,
    onProjectClick: (Project) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(
            items = content.workspaces,
            key = { item -> item.workspace.id.toString() },
        ) { item ->
            val workspace = item.workspace
            WorkspaceItem(
                workspace = item,
                icon = { content.icon(workspace) },
                projectImage = content.projectImage,
                onClick = { onWorkspaceClick(workspace) },
                onProjectClick = onProjectClick,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun WorkspaceItem(
    workspace: WorkspaceWithProjects,
    icon: @Composable () -> Unit,
    projectImage: @Composable (Project) -> Unit,
    onClick: () -> Unit,
    onProjectClick: (Project) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(true) }

    Column(modifier = modifier) {
        WorkspaceHeader(
            workspace = workspace.workspace,
            icon = icon,
            onClick = onClick,
            expanded = expanded,
            onExpandedChange = { expanded = it },
            modifier = Modifier.fillMaxWidth(),
        )
        WorkspaceProjects(
            projects = workspace.projects,
            expanded = expanded,
            projectImage = projectImage,
            onProjectClick = onProjectClick,
        )
    }
}

@Composable
private fun WorkspaceHeader(
    workspace: Workspace,
    icon: @Composable () -> Unit,
    onClick: () -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        onClick = onClick,
        modifier = modifier,
        tonalElevation = 1.dp,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Row(
                modifier = Modifier.padding(16.dp).weight(1f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                icon()
                Spacer(modifier = Modifier.width(16.dp))
                OneLineTitle(
                    text = workspace.data.name,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            IconButton(onClick = { onExpandedChange(!expanded) }) {
                ExpandedIcon(expanded = expanded)
            }
        }
    }
}

@Composable
private fun WorkspaceProjects(
    projects: List<Project>,
    expanded: Boolean,
    projectImage: @Composable (Project) -> Unit,
    onProjectClick: (Project) -> Unit,
) {
    AnimatedVisibility(
        visible = expanded,
        enter = expandVertically(expandFrom = Alignment.Top),
        exit = shrinkVertically(shrinkTowards = Alignment.Top),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            projects.forEach { project ->
                ProjectItem(
                    project = project,
                    image = { projectImage(project) },
                    onClick = { onProjectClick(project) },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProjectItem(
    project: Project,
    image: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedCard(
        onClick = onClick,
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                OneLineTitle(
                    text = project.data.name,
                    style = MaterialTheme.typography.titleMedium,
                )
                OneLineTitle(text = project.data.description)
            }
            image()
        }
    }
}
