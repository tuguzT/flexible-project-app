package io.github.tuguzt.flexibleproject.domain.model

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

sealed interface Result<T, E> {
    val data: T?
    val error: E?

    data class Success<T, E>(
        override val data: T,
    ) : Result<T, E> {
        override val error = null
    }

    data class Error<T, E>(
        override val error: E,
        override val data: T? = null,
    ) : Result<T, E>
}

fun <T, E> success(data: T): Result.Success<T, E> = Result.Success(data)

fun <T, E> error(error: E, data: T? = null): Result.Error<T, E> =
    Result.Error(error, data)

@OptIn(ExperimentalContracts::class)
fun <T, E, U> Result<T, E>.map(transform: (T) -> U): Result<U, E> {
    contract {
        callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
    }
    return when (this) {
        is Result.Success -> map(transform)
        is Result.Error -> map(transform)
    }
}

@OptIn(ExperimentalContracts::class)
fun <T, E, N> Result<T, E>.mapError(transform: (E) -> N): Result<T, N> {
    contract {
        callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
    }
    return when (this) {
        is Result.Success -> mapError(transform)
        is Result.Error -> mapError(transform)
    }
}

@OptIn(ExperimentalContracts::class)
fun <T, E, U> Result.Success<T, E>.map(transform: (T) -> U): Result.Success<U, E> {
    contract {
        callsInPlace(transform, InvocationKind.EXACTLY_ONCE)
    }
    return Result.Success(data = transform(data))
}

@OptIn(ExperimentalContracts::class)
fun <T, E, N> Result.Success<T, E>.mapError(transform: (E) -> N): Result.Success<T, N> {
    contract {
        callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
    }
    return Result.Success(data)
}

@OptIn(ExperimentalContracts::class)
fun <T, E, U> Result.Error<T, E>.map(transform: (T) -> U): Result.Error<U, E> {
    contract {
        callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
    }
    return Result.Error(error = error, data = data?.let(transform))
}

@OptIn(ExperimentalContracts::class)
fun <T, E, N> Result.Error<T, E>.mapError(transform: (E) -> N): Result.Error<T, N> {
    contract {
        callsInPlace(transform, InvocationKind.EXACTLY_ONCE)
    }
    return Result.Error(error = transform(error), data = data)
}
