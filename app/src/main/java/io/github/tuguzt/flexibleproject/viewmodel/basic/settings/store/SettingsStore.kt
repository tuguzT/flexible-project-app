package io.github.tuguzt.flexibleproject.viewmodel.basic.settings.store

import com.arkivanov.mvikotlin.core.store.Store
import io.github.tuguzt.flexibleproject.domain.model.settings.Language
import io.github.tuguzt.flexibleproject.domain.model.settings.Theme
import io.github.tuguzt.flexibleproject.viewmodel.basic.settings.store.SettingsStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.basic.settings.store.SettingsStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.basic.settings.store.SettingsStore.State

interface SettingsStore : Store<Intent, State, Label> {
    sealed interface Intent {
        data class ChangeTheme(val theme: Theme) : Intent
        data class ChangeLanguage(val language: Language) : Intent
    }

    data class State(
        val theme: Theme,
        val language: Language,
        val loading: Boolean,
    )

    sealed interface Label {
        object LocalStoreError : Label
        object NetworkAccessError : Label
        object UnknownError : Label
    }
}
