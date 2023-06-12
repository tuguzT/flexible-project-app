package io.github.tuguzt.flexibleproject.view.screens.basic.project

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.domain.model.project.Visibility
import io.github.tuguzt.flexibleproject.view.theme.AppTheme
import io.github.tuguzt.flexibleproject.view.utils.OneLineTitle
import io.github.tuguzt.flexibleproject.view.utils.SingleLineTextField
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
        SingleLineTextField(
            value = visibility.toTranslatedString(),
            onValueChange = {},
            label = label,
            modifier = modifier.menuAnchor(),
            readOnly = true,
            enabled = enabled,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) },
        ) {
            Visibility.values().forEach { visibility ->
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

@Preview
@Composable
private fun VisibilityExposedDropdownMenu() {
    AppTheme {
        Surface {
            VisibilityExposedDropdownMenu(
                visibility = Visibility.Public,
                onVisibilityChange = {},
                expanded = true,
                onExpandedChange = {},
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
