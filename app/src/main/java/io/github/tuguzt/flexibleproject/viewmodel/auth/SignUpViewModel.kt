package io.github.tuguzt.flexibleproject.viewmodel.auth

import androidx.lifecycle.viewModelScope
import com.arkivanov.mvikotlin.core.store.StoreFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.tuguzt.flexibleproject.domain.usecase.user.SignUp
import io.github.tuguzt.flexibleproject.viewmodel.StoreViewModel
import io.github.tuguzt.flexibleproject.viewmodel.auth.store.SignUpStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.auth.store.SignUpStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.auth.store.SignUpStore.State
import io.github.tuguzt.flexibleproject.viewmodel.auth.store.SignUpStoreProvider
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    signUp: SignUp,
    storeFactory: StoreFactory,
) : StoreViewModel<Intent, State, Label>() {
    override val provider = SignUpStoreProvider(
        signUp = signUp,
        storeFactory = storeFactory,
        coroutineContext = viewModelScope.coroutineContext,
    )
}
