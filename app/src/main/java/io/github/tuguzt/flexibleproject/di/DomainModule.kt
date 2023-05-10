package io.github.tuguzt.flexibleproject.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.tuguzt.flexibleproject.domain.repository.user.UserRepository
import io.github.tuguzt.flexibleproject.domain.repository.workspace.WorkspaceRepository
import io.github.tuguzt.flexibleproject.domain.usecase.user.FindUserById
import io.github.tuguzt.flexibleproject.domain.usecase.workspace.CreateWorkspace
import io.github.tuguzt.flexibleproject.domain.usecase.workspace.FindWorkspaceById
import io.github.tuguzt.flexibleproject.domain.usecase.workspace.ReadAllWorkspaces
import io.github.tuguzt.flexibleproject.domain.usecase.workspace.WorkspacesFlow

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Provides
    fun provideFindUserById(repository: UserRepository) = FindUserById(repository)

    @Provides
    fun provideReadAllWorkspaces(repository: WorkspaceRepository) = ReadAllWorkspaces(repository)

    @Provides
    fun provideFindWorkspaceById(repository: WorkspaceRepository) = FindWorkspaceById(repository)

    @Provides
    fun provideCreateWorkspace(repository: WorkspaceRepository) = CreateWorkspace(repository)

    @Provides
    fun provideWorkspacesFlow(repository: WorkspaceRepository) = WorkspacesFlow(repository)
}
