package io.github.tuguzt.flexibleproject.viewmodel.auth

import io.github.tuguzt.flexibleproject.viewmodel.BackendErrorKind
import io.github.tuguzt.flexibleproject.viewmodel.MessageKind

sealed interface AuthMessageKind : MessageKind {
    data class Backend(val backendErrorKind: BackendErrorKind) : AuthMessageKind {
        companion object {
            fun server(): Backend = Backend(BackendErrorKind.ServerError)
            fun network(): Backend = Backend(BackendErrorKind.NetworkError)
            fun unknown(): Backend = Backend(BackendErrorKind.UnknownError)
        }
    }
}
