package io.github.tuguzt.flexibleproject.view.screens.basic.project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.material.placeholder
import io.github.tuguzt.flexibleproject.domain.model.project.Project
import io.github.tuguzt.flexibleproject.domain.model.project.Visibility
import io.github.tuguzt.flexibleproject.view.utils.toTranslatedString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectContent(
    project: Project?,
    loading: Boolean,
    onNavigationClick: () -> Unit,
    topBarActions: @Composable RowScope.() -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(),
) {
    Scaffold(
        topBar = {
            ProjectTopBar(
                project = project,
                loading = loading,
                onNavigationClick = onNavigationClick,
                actions = topBarActions,
                scrollBehavior = scrollBehavior,
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState()),
        ) {
            ProjectDataCard(
                project = project,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun ProjectDataCard(
    project: Project?,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        tonalElevation = 1.dp,
    ) {
        Column {
            ProjectDescription(
                description = project?.data?.description,
                modifier = Modifier.padding(16.dp),
            )
            ProjectVisibility(
                visibility = project?.data?.visibility,
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}

@Composable
private fun ProjectDescription(
    description: String?,
    modifier: Modifier = Modifier,
) {
    ProjectItemRow(
        data = description,
        icon = Icons.Rounded.Description,
        modifier = modifier,
    )
}

@Composable
private fun ProjectVisibility(
    visibility: Visibility?,
    modifier: Modifier = Modifier,
) {
    ProjectItemRow(
        data = visibility?.toTranslatedString(),
        icon = Icons.Rounded.Visibility,
        modifier = modifier,
        placeholder = Visibility.Public.toTranslatedString(),
    )
}

@Composable
private fun ProjectItemRow(
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
