package io.github.tuguzt.flexibleproject.viewmodel.main.home

import io.github.tuguzt.flexibleproject.viewmodel.BackendErrorKind
import io.github.tuguzt.flexibleproject.viewmodel.MessageKind

sealed interface HomeViewModelMessageKind : MessageKind {
    data class Backend(val backendErrorKind: BackendErrorKind) : HomeViewModelMessageKind {
        companion object {
            fun server(): Backend = Backend(BackendErrorKind.ServerError)
            fun network(): Backend = Backend(BackendErrorKind.NetworkError)
            fun unknown(): Backend = Backend(BackendErrorKind.UnknownError)
        }
    }
}
