package io.github.tuguzt.flexibleproject.view.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
) {
    Image(
        imageVector = Icons.Rounded.Person,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clip(CircleShape)
            .placeholder(visible = user == null),
    )
    // TODO load image from user avatar url
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
            avatarUrl = null,
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