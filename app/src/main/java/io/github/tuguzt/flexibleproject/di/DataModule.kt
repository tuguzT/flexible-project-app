package io.github.tuguzt.flexibleproject.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.tuguzt.flexibleproject.data.DatabaseClient
import io.github.tuguzt.flexibleproject.data.RemoteClient
import io.github.tuguzt.flexibleproject.data.repository.user.MockUserRepository
import io.github.tuguzt.flexibleproject.data.repository.workspace.MockWorkspaceRepository
import io.github.tuguzt.flexibleproject.domain.repository.user.UserRepository
import io.github.tuguzt.flexibleproject.domain.repository.workspace.WorkspaceRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Singleton
    @Provides
    fun providesDatabaseClient(@ApplicationContext context: Context): DatabaseClient =
        DatabaseClient(context)

    @Singleton
    @Provides
    fun providesRemoteClient(): RemoteClient =
        RemoteClient("http://localhost:8080/graphql")

    @Singleton
    @Provides
    fun provideUserRepository(): UserRepository = MockUserRepository()

    @Singleton
    @Provides
    fun provideWorkspaceRepository(): WorkspaceRepository = MockWorkspaceRepository()
}
