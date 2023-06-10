package io.github.tuguzt.flexibleproject.viewmodel.basic.user

import androidx.lifecycle.viewModelScope
import com.arkivanov.mvikotlin.core.store.StoreFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.tuguzt.flexibleproject.domain.usecase.user.GetCurrentUser
import io.github.tuguzt.flexibleproject.domain.usecase.user.UpdateCurrentUser
import io.github.tuguzt.flexibleproject.viewmodel.StoreViewModel
import io.github.tuguzt.flexibleproject.viewmodel.basic.user.store.UpdateUserStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.basic.user.store.UpdateUserStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.basic.user.store.UpdateUserStore.State
import io.github.tuguzt.flexibleproject.viewmodel.basic.user.store.UpdateUserStoreProvider
import javax.inject.Inject

@HiltViewModel
class UpdateUserViewModel @Inject constructor(
    currentUser: GetCurrentUser,
    updateUser: UpdateCurrentUser,
    storeFactory: StoreFactory,
) : StoreViewModel<Intent, State, Label>() {
    override val provider = UpdateUserStoreProvider(
        currentUser = currentUser,
        updateUser = updateUser,
        storeFactory = storeFactory,
        coroutineContext = viewModelScope.coroutineContext,
    )
}
