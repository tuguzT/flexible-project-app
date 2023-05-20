package io.github.tuguzt.flexibleproject.data

import com.apollographql.apollo3.ApolloClient

class RemoteClient(serverUrl: String) {
    internal val client = ApolloClient.Builder()
        .serverUrl(serverUrl)
        .build()
}
