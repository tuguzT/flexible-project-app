package io.github.tuguzt.flexibleproject.view.screens.basic.user

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.material.placeholder
import io.github.tuguzt.flexibleproject.domain.model.Id
import io.github.tuguzt.flexibleproject.domain.model.user.Role
import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.model.user.UserData
import io.github.tuguzt.flexibleproject.view.theme.AppTheme
import io.github.tuguzt.flexibleproject.view.utils.ImageByUrl
import io.github.tuguzt.flexibleproject.view.utils.ImageError
import io.github.tuguzt.flexibleproject.view.utils.NavigateUpIconButton
import io.github.tuguzt.flexibleproject.view.utils.OneLineTitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserTopBar(
    user: User?,
    loading: Boolean,
    onNavigationClick: () -> Unit,
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    val collapsedFraction by remember {
        derivedStateOf { scrollBehavior?.state?.collapsedFraction ?: 0.0f }
    }
    val collapsed by remember {
        derivedStateOf { collapsedFraction > 0.8 }
    }

    val surfaceColor = MaterialTheme.colorScheme.surface
    val containerColor by remember {
        derivedStateOf { surfaceColor.copy(alpha = collapsedFraction) }
    }
    val onSurfaceColor = MaterialTheme.colorScheme.onSurface
    val contentColor by remember {
        derivedStateOf { if (collapsed) onSurfaceColor else Color.White }
    }
    val onSurfaceVariantColor = MaterialTheme.colorScheme.onSurfaceVariant
    val actionContentColor by remember {
        derivedStateOf { if (collapsed) onSurfaceVariantColor else Color.White }
    }

    val gradient = remember {
        val cornerColor = Color.Black.copy(alpha = 0.25f)
        val colors = listOf(cornerColor, Color.Transparent, cornerColor)
        Brush.verticalGradient(colors)
    }

    Box {
        ImageByUrl(
            url = user?.data?.avatar,
            modifier = Modifier
                .matchParentSize()
                .drawWithContent {
                    drawContent()
                    drawRect(gradient)
                },
            error = {
                ImageError(imageVector = Icons.Rounded.Person, imageSize = 96.dp)
            },
        )
        LargeTopAppBar(
            modifier = modifier,
            title = {
                UserTitle(
                    user = user,
                    avatarVisible = collapsed,
                )
            },
            navigationIcon = {
                NavigateUpIconButton(
                    onClick = onNavigationClick,
                    enabled = !loading,
                )
            },
            scrollBehavior = scrollBehavior,
            colors = TopAppBarDefaults.largeTopAppBarColors(
                containerColor = containerColor,
                navigationIconContentColor = contentColor,
                titleContentColor = contentColor,
                actionIconContentColor = actionContentColor,
            ),
            actions = actions,
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

@Composable
private fun UserTitle(
    user: User?,
    avatarVisible: Boolean,
) {
    Row(
        modifier = Modifier.alpha(LocalContentColor.current.alpha),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (avatarVisible) {
            ImageByUrl(
                url = user?.data?.avatar,
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape),
                error = {
                    ImageError(imageVector = Icons.Rounded.Person, imageSize = 32.dp)
                },
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        OneLineTitle(
            text = user?.data?.displayName ?: "Placeholder",
            modifier = Modifier.placeholder(visible = user == null),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun UserTopBarWithUser() {
    val user = User(
        id = Id("1"),
        data = UserData(
            name = "tuguzT",
            displayName = "Timur Tugushev",
            role = Role.User,
            email = "timurka.tugushev@gmail.com",
            avatar = "https://avatars.githubusercontent.com/u/56771526",
        ),
    )

    AppTheme {
        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

        Scaffold(
            topBar = {
                UserTopBar(
                    user = user,
                    loading = false,
                    onNavigationClick = {},
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
                contentAlignment = Alignment.Center,
            ) {
                Text(text = "Some example text")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun UserTopBarWithoutUser() {
    AppTheme {
        Scaffold(
            topBar = {
                UserTopBar(
                    user = null,
                    loading = true,
                    onNavigationClick = {},
                )
            },
        ) { padding ->
            Box(modifier = Modifier.padding(padding))
        }
    }
}
