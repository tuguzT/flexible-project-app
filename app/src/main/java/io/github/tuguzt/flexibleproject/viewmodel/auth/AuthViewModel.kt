package io.github.tuguzt.flexibleproject.viewmodel.auth

import androidx.lifecycle.viewModelScope
import com.arkivanov.mvikotlin.core.store.StoreFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.tuguzt.flexibleproject.viewmodel.StoreViewModel
import io.github.tuguzt.flexibleproject.viewmodel.auth.store.AuthStore
import io.github.tuguzt.flexibleproject.viewmodel.auth.store.AuthStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.auth.store.AuthStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.auth.store.AuthStore.State
import io.github.tuguzt.flexibleproject.viewmodel.auth.store.AuthStoreProvider
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    storeFactory: StoreFactory,
) : StoreViewModel<Intent, State, Label>() {
    private val provider = AuthStoreProvider(
        storeFactory = storeFactory,
        coroutineContext = viewModelScope.coroutineContext,
    )
    override val store: AuthStore = provider.provide()
}
