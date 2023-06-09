package io.github.tuguzt.flexibleproject.view.screens.user

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle

@Destination(style = DestinationStyle.Dialog::class)
@Composable
fun DeleteUser(
    resultNavigator: ResultBackNavigator<Boolean>,
) {
    DeleteUserContent(
        onConfirmation = { confirmation ->
            resultNavigator.navigateBack(result = confirmation)
        },
    )
}
