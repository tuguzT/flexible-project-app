package io.github.tuguzt.flexibleproject.view.screens.basic.home

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import io.github.tuguzt.flexibleproject.view.screens.basic.BasicNavGraph

@BasicNavGraph(start = true)
@Destination
@Composable
fun HomeScreen() {
    HomeContent(onAddClick = { /* TODO */ })
}
