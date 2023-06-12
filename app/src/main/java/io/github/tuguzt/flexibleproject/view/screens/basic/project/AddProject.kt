package io.github.tuguzt.flexibleproject.view.screens.basic.project

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.tuguzt.flexibleproject.domain.model.project.Visibility
import io.github.tuguzt.flexibleproject.view.screens.basic.BasicNavGraph

@BasicNavGraph
@Destination
@Composable
fun AddProject(
    workspaceId: String? = null,
    navigator: DestinationsNavigator,
) {
    AddProjectContent(
        name = "", // TODO
        onNameChange = { /* TODO */ },
        description = "", // TODO
        onDescriptionChange = { /* TODO */ },
        visibility = Visibility.Public, // TODO
        onVisibilityChange = { /* TODO */ },
        allWorkspaces = listOf(), // TODO
        workspace = null, // TODO
        onWorkspaceChange = { /* TODO */ },
        loading = false, // TODO
        valid = false, // TODO
        onAddProjectClick = { /* TODO */ },
        onNavigationClick = navigator::navigateUp,
    )
}
