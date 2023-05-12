package io.github.tuguzt.flexibleproject.view.utils

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@SuppressLint("ComposableNaming")
@Composable
inline fun <reified T> Flow<T>.collectInLaunchedEffect(
    context: CoroutineContext = EmptyCoroutineContext,
    noinline action: suspend (value: T) -> Unit,
) {
    LaunchedEffect(this, context) {
        if (context == EmptyCoroutineContext) {
            this@collectInLaunchedEffect.collect(action)
        } else withContext(context) {
            this@collectInLaunchedEffect.collect(action)
        }
    }
}

@SuppressLint("ComposableNaming")
@Composable
inline fun <reified T> Flow<T>.collectInLaunchedEffectWithLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext,
    noinline action: suspend (value: T) -> Unit,
) {
    val lifecycle = lifecycleOwner.lifecycle
    LaunchedEffect(this, lifecycle, minActiveState, context) {
        lifecycle.repeatOnLifecycle(minActiveState) {
            if (context == EmptyCoroutineContext) {
                this@collectInLaunchedEffectWithLifecycle.collect(action)
            } else withContext(context) {
                this@collectInLaunchedEffectWithLifecycle.collect(action)
            }
        }
    }
}
