package io.github.tuguzt.flexibleproject.viewmodel.basic.settings

import androidx.lifecycle.viewModelScope
import com.arkivanov.mvikotlin.core.store.StoreFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.tuguzt.flexibleproject.domain.usecase.settings.GetSettings
import io.github.tuguzt.flexibleproject.domain.usecase.settings.UpdateSettings
import io.github.tuguzt.flexibleproject.viewmodel.StoreViewModel
import io.github.tuguzt.flexibleproject.viewmodel.basic.settings.store.SettingsStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.basic.settings.store.SettingsStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.basic.settings.store.SettingsStore.State
import io.github.tuguzt.flexibleproject.viewmodel.basic.settings.store.SettingsStoreProvider
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    settings: GetSettings,
    update: UpdateSettings,
    storeFactory: StoreFactory,
) : StoreViewModel<Intent, State, Label>() {
    override val provider = SettingsStoreProvider(
        settings = settings,
        update = update,
        storeFactory = storeFactory,
        coroutineContext = viewModelScope.coroutineContext,
    )
}
