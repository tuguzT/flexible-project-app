package io.github.tuguzt.flexibleproject.viewmodel

import com.arkivanov.mvikotlin.core.store.Store

interface StoreProvider<in Intent : Any, out State : Any, out Label : Any> {
    fun provide(): Store<Intent, State, Label>
}
