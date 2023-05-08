package io.github.tuguzt.flexibleproject.viewmodel.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.tuguzt.flexibleproject.domain.model.user.UserId
import io.github.tuguzt.flexibleproject.viewmodel.user.store.UserStore
import io.github.tuguzt.flexibleproject.viewmodel.user.store.UserStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.user.store.UserStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.user.store.UserStore.State
import io.github.tuguzt.flexibleproject.viewmodel.user.store.UserStoreProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    storeFactory: StoreFactory,
) : ViewModel() {
    private val provider = UserStoreProvider(
        storeFactory = storeFactory,
        coroutineContext = viewModelScope.coroutineContext,
    )
    private val store: UserStore = provider.provide()

    // FIXME remove this when current user view model will be created
    init {
        val intent = Intent.Load(id = UserId("user"))
        accept(intent)
    }

    fun accept(intent: Intent): Unit = store.accept(intent)

    val stateFlow: StateFlow<State> = store.stateFlow(scope = viewModelScope)

    val labels: Flow<Label> = store.labels
}
