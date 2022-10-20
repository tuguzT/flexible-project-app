package io.github.tuguzt.flexibleproject.data.datasource.remote

import com.apollographql.apollo3.ApolloClient
import io.github.tuguzt.flexibleproject.domain.model.UserToken

class ClientAuthRequired(serverUrl: String, token: UserToken) {
    val client: ApolloClient = ApolloClient.Builder()
        .serverUrl(serverUrl)
        .addHttpInterceptor(AuthInterceptor(token))
        .build()
}

class Client(serverUrl: String) {
    val client: ApolloClient = ApolloClient.Builder()
        .serverUrl(serverUrl)
        .build()
}
