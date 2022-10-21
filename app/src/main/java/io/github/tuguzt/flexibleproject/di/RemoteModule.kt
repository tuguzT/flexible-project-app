package io.github.tuguzt.flexibleproject.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.tuguzt.flexibleproject.BuildConfig
import io.github.tuguzt.flexibleproject.data.datasource.remote.Client
import io.github.tuguzt.flexibleproject.data.datasource.remote.ClientAuthRequired
import io.github.tuguzt.flexibleproject.domain.model.UserToken

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    private const val token = "" // todo

    @Provides
    fun providesClient(): Client =
        Client(BuildConfig.backendUrl)

    @Provides
    fun providesClientAuthRequired(): ClientAuthRequired =
        ClientAuthRequired(BuildConfig.backendUrl, token = UserToken(token))
}
