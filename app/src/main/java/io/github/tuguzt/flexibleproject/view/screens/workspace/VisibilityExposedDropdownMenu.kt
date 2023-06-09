package io.github.tuguzt.flexibleproject.view.screens.workspace

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.domain.model.workspace.Visibility
import io.github.tuguzt.flexibleproject.view.theme.AppTheme
import io.github.tuguzt.flexibleproject.view.utils.OneLineTitle
import io.github.tuguzt.flexibleproject.view.utils.toTranslatedString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisibilityExposedDropdownMenu(
    visibility: Visibility,
    onVisibilityChange: (Visibility) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String = stringResource(R.string.visibility),
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandedChange,
    ) {
        OutlinedTextField(
            value = visibility.toTranslatedString(),
            onValueChange = {},
            modifier = modifier.menuAnchor(),
            readOnly = true,
            enabled = enabled,
            label = { OneLineTitle(text = label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            singleLine = true,
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) },
        ) {
            LazyColumn {
                items(Visibility.values(), key = Visibility::ordinal) { visibility ->
                    DropdownMenuItem(
                        text = { OneLineTitle(text = visibility.toTranslatedString()) },
                        onClick = {
                            onVisibilityChange(visibility)
                            onExpandedChange(false)
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun VisibilityExposedDropdownMenu() {
    AppTheme {
        Surface {
            VisibilityExposedDropdownMenu(
                visibility = Visibility.Public,
                onVisibilityChange = {},
                expanded = false,
                onExpandedChange = {},
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
