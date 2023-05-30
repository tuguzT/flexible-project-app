package io.github.tuguzt.flexibleproject.viewmodel.user

import androidx.lifecycle.viewModelScope
import com.arkivanov.mvikotlin.core.store.StoreFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.tuguzt.flexibleproject.domain.usecase.user.GetCurrentUser
import io.github.tuguzt.flexibleproject.domain.usecase.user.SignOut
import io.github.tuguzt.flexibleproject.viewmodel.StoreViewModel
import io.github.tuguzt.flexibleproject.viewmodel.user.store.CurrentUserStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.user.store.CurrentUserStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.user.store.CurrentUserStore.State
import io.github.tuguzt.flexibleproject.viewmodel.user.store.CurrentUserStoreProvider
import javax.inject.Inject

@HiltViewModel
class CurrentUserViewModel @Inject constructor(
    currentUser: GetCurrentUser,
    signOut: SignOut,
    storeFactory: StoreFactory,
) : StoreViewModel<Intent, State, Label>() {
    override val provider = CurrentUserStoreProvider(
        currentUser = currentUser,
        signOut = signOut,
        storeFactory = storeFactory,
        coroutineContext = viewModelScope.coroutineContext,
    )
}
