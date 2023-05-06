package io.github.tuguzt.flexibleproject.view.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun WorkspaceScreen(id: String) {
    Text(text = "Workspace $id")
}
