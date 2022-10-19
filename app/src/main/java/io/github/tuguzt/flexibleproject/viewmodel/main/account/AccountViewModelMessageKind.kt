package io.github.tuguzt.flexibleproject.viewmodel.main.account

import io.github.tuguzt.flexibleproject.viewmodel.BackendErrorKind
import io.github.tuguzt.flexibleproject.viewmodel.MessageKind

sealed interface AccountViewModelMessageKind : MessageKind {
    data class Backend(val backendErrorKind: BackendErrorKind) : AccountViewModelMessageKind {
        companion object {
            fun server(): Backend = Backend(BackendErrorKind.ServerError)
            fun network(): Backend = Backend(BackendErrorKind.NetworkError)
            fun unknown(): Backend = Backend(BackendErrorKind.UnknownError)
        }
    }
}
