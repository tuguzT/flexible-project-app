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
import io.github.tuguzt.flexibleproject.domain.model.user.Role
import io.github.tuguzt.flexibleproject.domain.model.user.User
import io.github.tuguzt.flexibleproject.domain.model.user.UserData
import io.github.tuguzt.flexibleproject.view.theme.AppTheme

@Composable
fun UserAvatar(
    user: User?,
    modifier: Modifier = Modifier,
    contentDescription: String? = stringResource(R.string.user_avatar),
    crossfade: Boolean = true,
    colorFilter: ColorFilter? = null,
    error: @Composable (() -> Unit)? = null,
) {
    val model = ImageRequest.Builder(LocalContext.current)
        .data(user?.data?.avatarUrl)
        .crossfade(crossfade)
        .build()
    var isLoading by remember { mutableStateOf(true) }

    SubcomposeAsyncImage(
        model = model,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = modifier.placeholder(visible = isLoading || user == null),
        colorFilter = colorFilter,
        error = error?.let { { it() } },
        onLoading = { isLoading = true },
        onSuccess = { isLoading = false },
        onError = { isLoading = false },
    )
}

@Preview
@Composable
private fun UserAvatarWithUser() {
    val user = User(
        id = Id("user"),
        data = UserData(
            name = "tuguzT",
            displayName = "Timur Tugushev",
            role = Role.User,
            email = "timurka.tugushev@gmail.com",
            avatarUrl = "https://avatars.githubusercontent.com/u/56771526",
        ),
    )

    AppTheme {
        UserAvatar(
            user = user,
            modifier = Modifier.size(72.dp),
        )
    }
}

@Preview
@Composable
private fun UserAvatarWithoutUser() {
    AppTheme {
        UserAvatar(
            user = null,
            modifier = Modifier.size(72.dp),
        )
    }
}
