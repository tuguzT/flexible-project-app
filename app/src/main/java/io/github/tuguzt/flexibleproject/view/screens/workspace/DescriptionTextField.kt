package io.github.tuguzt.flexibleproject.view.screens.workspace

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.view.theme.AppTheme
import io.github.tuguzt.flexibleproject.view.utils.MultiLineTextField

@Composable
fun DescriptionTextField(
    description: String,
    onDescriptionChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String = stringResource(R.string.description),
) {
    MultiLineTextField(
        value = description,
        onValueChange = onDescriptionChange,
        modifier = modifier,
        enabled = enabled,
        label = label,
    )
}

@Preview
@Composable
private fun DescriptionTextField() {
    AppTheme {
        Surface {
            DescriptionTextField(
                description = """
                    Some very long description
                    Can be multiline
                    
                    AND MORE!!!
                """.trimIndent(),
                onDescriptionChange = {},
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
