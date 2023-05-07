package io.github.tuguzt.flexibleproject.view.screens.basic.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.ramcosta.composedestinations.annotation.Destination
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.view.screens.basic.BasicNavGraph

@BasicNavGraph(start = true)
@Destination
@Composable
fun HomeScreen() {
    Text(text = stringResource(R.string.home))
}
