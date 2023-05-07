package io.github.tuguzt.flexibleproject.view.screens.workspace

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph
@Destination
@Composable
fun WorkspaceScreen(id: String) {
    Text(text = "Workspace by ID $id")
}
