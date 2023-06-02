package io.github.tuguzt.flexibleproject.viewmodel.user

import androidx.lifecycle.viewModelScope
import com.arkivanov.mvikotlin.core.store.StoreFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.tuguzt.flexibleproject.domain.usecase.user.FilterUsers
import io.github.tuguzt.flexibleproject.viewmodel.StoreViewModel
import io.github.tuguzt.flexibleproject.viewmodel.user.store.UserStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.user.store.UserStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.user.store.UserStore.State
import io.github.tuguzt.flexibleproject.viewmodel.user.store.UserStoreProvider
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    users: FilterUsers,
    storeFactory: StoreFactory,
) : StoreViewModel<Intent, State, Label>() {
    override val provider = UserStoreProvider(
        users = users,
        storeFactory = storeFactory,
        coroutineContext = viewModelScope.coroutineContext,
    )
}
