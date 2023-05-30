package io.github.tuguzt.flexibleproject.viewmodel.auth

import androidx.lifecycle.viewModelScope
import com.arkivanov.mvikotlin.core.store.StoreFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.tuguzt.flexibleproject.domain.usecase.user.SignIn
import io.github.tuguzt.flexibleproject.viewmodel.StoreViewModel
import io.github.tuguzt.flexibleproject.viewmodel.auth.store.SignInStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.auth.store.SignInStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.auth.store.SignInStore.State
import io.github.tuguzt.flexibleproject.viewmodel.auth.store.SignInStoreProvider
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    signIn: SignIn,
    storeFactory: StoreFactory,
) : StoreViewModel<Intent, State, Label>() {
    override val provider = SignInStoreProvider(
        signIn = signIn,
        storeFactory = storeFactory,
        coroutineContext = viewModelScope.coroutineContext,
    )
}
