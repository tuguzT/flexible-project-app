package io.github.tuguzt.flexibleproject.data.datasource.remote

import com.apollographql.apollo3.api.http.HttpRequest
import com.apollographql.apollo3.api.http.HttpResponse
import com.apollographql.apollo3.network.http.HttpInterceptor
import com.apollographql.apollo3.network.http.HttpInterceptorChain
import io.github.tuguzt.flexibleproject.domain.model.UserToken

class AuthInterceptor(private val token: UserToken) : HttpInterceptor {
    override suspend fun intercept(
        request: HttpRequest,
        chain: HttpInterceptorChain,
    ): HttpResponse {
        val tokenRequest = request.newBuilder()
            .addHeader("Authorization", "Bearer ${token.token}")
            .build()
        return chain.proceed(tokenRequest)
    }
}
