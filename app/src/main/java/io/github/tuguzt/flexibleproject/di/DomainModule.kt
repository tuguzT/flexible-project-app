package io.github.tuguzt.flexibleproject.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.tuguzt.flexibleproject.domain.repository.user.CurrentUserRepository
import io.github.tuguzt.flexibleproject.domain.repository.user.UserRepository
import io.github.tuguzt.flexibleproject.domain.repository.workspace.WorkspaceRepository
import io.github.tuguzt.flexibleproject.domain.usecase.user.DeleteCurrentUser
import io.github.tuguzt.flexibleproject.domain.usecase.user.FindUserById
import io.github.tuguzt.flexibleproject.domain.usecase.user.GetCurrentUser
import io.github.tuguzt.flexibleproject.domain.usecase.user.SignIn
import io.github.tuguzt.flexibleproject.domain.usecase.user.SignOut
import io.github.tuguzt.flexibleproject.domain.usecase.user.SignUp
import io.github.tuguzt.flexibleproject.domain.usecase.user.UpdateCurrentUser
import io.github.tuguzt.flexibleproject.domain.usecase.workspace.CreateWorkspace
import io.github.tuguzt.flexibleproject.domain.usecase.workspace.FilterWorkspaces
import io.github.tuguzt.flexibleproject.domain.usecase.workspace.FindWorkspaceById
import io.github.tuguzt.flexibleproject.domain.usecase.workspace.WorkspacesFlow

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Provides
    fun provideFindUserById(repository: UserRepository) = FindUserById(repository)

    @Provides
    fun provideSignIn(
        userRepository: UserRepository,
        currentUserRepository: CurrentUserRepository,
    ) = SignIn(userRepository, currentUserRepository)

    @Provides
    fun provideSignUp(
        userRepository: UserRepository,
        currentUserRepository: CurrentUserRepository,
    ) = SignUp(userRepository, currentUserRepository)

    @Provides
    fun provideSignOut(
        userRepository: UserRepository,
        currentUserRepository: CurrentUserRepository,
    ) = SignOut(userRepository, currentUserRepository)

    @Provides
    fun provideUpdateCurrentUser(
        userRepository: UserRepository,
        currentUserRepository: CurrentUserRepository,
    ) = UpdateCurrentUser(userRepository, currentUserRepository)

    @Provides
    fun provideDeleteCurrentUser(
        userRepository: UserRepository,
        currentUserRepository: CurrentUserRepository,
    ) = DeleteCurrentUser(userRepository, currentUserRepository)

    @Provides
    fun provideGetCurrentUser(repository: CurrentUserRepository) = GetCurrentUser(repository)

    @Provides
    fun provideFilterWorkspaces(repository: WorkspaceRepository) = FilterWorkspaces(repository)

    @Provides
    fun provideFindWorkspaceById(repository: WorkspaceRepository) = FindWorkspaceById(repository)

    @Provides
    fun provideCreateWorkspace(repository: WorkspaceRepository) = CreateWorkspace(repository)

    @Provides
    fun provideWorkspacesFlow(repository: WorkspaceRepository) = WorkspacesFlow(repository)
}
