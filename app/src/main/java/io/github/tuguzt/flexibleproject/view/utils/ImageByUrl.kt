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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.google.accompanist.placeholder.material.placeholder
import io.github.tuguzt.flexibleproject.view.theme.AppTheme

@Composable
fun ImageByUrl(
    url: String?,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    crossfade: Boolean = true,
    colorFilter: ColorFilter? = null,
    error: @Composable (() -> Unit)? = null,
) {
    val model = ImageRequest.Builder(LocalContext.current)
        .data(url)
        .crossfade(crossfade)
        .build()
    var loading by remember { mutableStateOf(true) }

    SubcomposeAsyncImage(
        model = model,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = modifier.placeholder(visible = loading),
        colorFilter = colorFilter,
        error = error?.let { { it() } },
        onLoading = { loading = true },
        onSuccess = { loading = false },
        onError = { loading = false },
    )
}

@Preview
@Composable
private fun ImageWithUrl() {
    AppTheme {
        ImageByUrl(
            url = "https://avatars.githubusercontent.com/u/56771526",
            modifier = Modifier.size(72.dp),
        )
    }
}

@Preview
@Composable
private fun ImageWithoutUrl() {
    AppTheme {
        ImageByUrl(
            url = null,
            modifier = Modifier.size(72.dp),
        )
    }
}
