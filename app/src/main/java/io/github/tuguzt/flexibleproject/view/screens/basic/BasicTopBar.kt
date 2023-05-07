package io.github.tuguzt.flexibleproject.view.screens.basic

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.view.utils.OneLineTitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicTopBar(
    onMenuClick: () -> Unit,
    modifier: Modifier = Modifier,
    title: String = stringResource(R.string.app_name),
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = { OneLineTitle(text = title) },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(Icons.Rounded.Menu, contentDescription = null)
            }
        },
    )
}
