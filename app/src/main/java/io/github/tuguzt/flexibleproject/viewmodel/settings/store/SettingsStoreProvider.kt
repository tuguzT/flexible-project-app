package io.github.tuguzt.flexibleproject.viewmodel.settings.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.github.tuguzt.flexibleproject.domain.model.BaseException
import io.github.tuguzt.flexibleproject.domain.model.Result
import io.github.tuguzt.flexibleproject.domain.model.settings.Language
import io.github.tuguzt.flexibleproject.domain.model.settings.Theme
import io.github.tuguzt.flexibleproject.domain.usecase.settings.GetSettings
import io.github.tuguzt.flexibleproject.domain.usecase.settings.UpdateSettings
import io.github.tuguzt.flexibleproject.viewmodel.StoreProvider
import io.github.tuguzt.flexibleproject.viewmodel.settings.store.SettingsStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.settings.store.SettingsStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.settings.store.SettingsStore.State
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import io.github.tuguzt.flexibleproject.domain.model.settings.UpdateSettings as UpdateSettingsInput

class SettingsStoreProvider(
    private val settings: GetSettings,
    private val update: UpdateSettings,
    private val storeFactory: StoreFactory,
    private val coroutineContext: CoroutineContext,
) : StoreProvider<Intent, State, Label> {
    override fun provide(): SettingsStore =
        object :
            SettingsStore,
            Store<Intent, State, Label> by storeFactory.create(
                name = SettingsStore::class.simpleName,
                initialState = State(
                    theme = Theme.System,
                    language = Language.System,
                    loading = true,
                ),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl,
            ) {}

    private sealed interface Message {
        object Loading : Message
        data class ThemeChanged(val theme: Theme) : Message
        data class LanguageChanged(val language: Language) : Message
        object Loaded : Message
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Unit, State, Message, Label>(
        mainContext = coroutineContext,
    ) {
        override fun executeAction(action: Unit, getState: () -> State) {
            scope.launch {
                settings.settings().collect { settings ->
                    dispatch(Message.ThemeChanged(settings.theme))
                    dispatch(Message.LanguageChanged(settings.language))
                    dispatch(Message.Loaded)
                }
            }
        }

        override fun executeIntent(intent: Intent, getState: () -> State) =
            when (intent) {
                is Intent.ChangeTheme -> changeTheme(intent.theme)
                is Intent.ChangeLanguage -> changeLanguage(intent.language)
            }

        private fun changeTheme(theme: Theme) {
            dispatch(Message.Loading)
            scope.launch {
                val input = UpdateSettingsInput(theme = theme)
                when (val result = update.update(input)) {
                    is Result.Success -> Unit
                    is Result.Error -> {
                        dispatch(Message.Loaded)
                        when (val error = result.error) {
                            is UpdateSettings.Exception.Repository -> when (error.error) {
                                is BaseException.LocalStore -> publish(Label.LocalStoreError)
                                is BaseException.NetworkAccess -> publish(Label.NetworkAccessError)
                                is BaseException.Unknown -> publish(Label.UnknownError)
                            }
                        }
                    }
                }
            }
        }

        private fun changeLanguage(language: Language) {
            dispatch(Message.Loading)
            scope.launch {
                val input = UpdateSettingsInput(language = language)
                when (val result = update.update(input)) {
                    is Result.Success -> Unit
                    is Result.Error -> {
                        dispatch(Message.Loaded)
                        when (val error = result.error) {
                            is UpdateSettings.Exception.Repository -> when (error.error) {
                                is BaseException.LocalStore -> publish(Label.LocalStoreError)
                                is BaseException.NetworkAccess -> publish(Label.NetworkAccessError)
                                is BaseException.Unknown -> publish(Label.UnknownError)
                            }
                        }
                    }
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Message> {
        override fun State.reduce(msg: Message): State =
            when (msg) {
                Message.Loading -> copy(loading = true)
                is Message.ThemeChanged -> copy(theme = msg.theme)
                is Message.LanguageChanged -> copy(language = msg.language)
                Message.Loaded -> copy(loading = false)
            }
    }
}
