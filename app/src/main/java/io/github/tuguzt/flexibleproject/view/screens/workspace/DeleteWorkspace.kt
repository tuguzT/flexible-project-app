package io.github.tuguzt.flexibleproject.view.screens.workspace

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.view.screens.basic.BasicNavGraph
import io.github.tuguzt.flexibleproject.view.utils.ConfirmDialog

@BasicNavGraph
@Destination(style = DestinationStyle.Dialog::class)
@Composable
fun DeleteWorkspace(
    resultNavigator: ResultBackNavigator<Boolean>,
) {
    ConfirmDialog(
        title = stringResource(R.string.delete),
        supportText = stringResource(R.string.delete_workspace_confirmation),
        onConfirmation = { confirmation ->
            resultNavigator.navigateBack(result = confirmation)
        },
    )
}
