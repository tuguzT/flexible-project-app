package io.github.tuguzt.flexibleproject.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.tuguzt.flexibleproject.domain.repository.project.ProjectRepository
import io.github.tuguzt.flexibleproject.domain.repository.settings.SettingsRepository
import io.github.tuguzt.flexibleproject.domain.repository.user.CurrentUserRepository
import io.github.tuguzt.flexibleproject.domain.repository.user.UserRepository
import io.github.tuguzt.flexibleproject.domain.repository.workspace.WorkspaceRepository
import io.github.tuguzt.flexibleproject.domain.usecase.project.CreateProject
import io.github.tuguzt.flexibleproject.domain.usecase.project.DeleteProject
import io.github.tuguzt.flexibleproject.domain.usecase.project.FilterProjects
import io.github.tuguzt.flexibleproject.domain.usecase.project.UpdateProject
import io.github.tuguzt.flexibleproject.domain.usecase.settings.GetSettings
import io.github.tuguzt.flexibleproject.domain.usecase.settings.UpdateSettings
import io.github.tuguzt.flexibleproject.domain.usecase.user.DeleteCurrentUser
import io.github.tuguzt.flexibleproject.domain.usecase.user.FilterUsers
import io.github.tuguzt.flexibleproject.domain.usecase.user.GetCurrentUser
import io.github.tuguzt.flexibleproject.domain.usecase.user.SignIn
import io.github.tuguzt.flexibleproject.domain.usecase.user.SignOut
import io.github.tuguzt.flexibleproject.domain.usecase.user.SignUp
import io.github.tuguzt.flexibleproject.domain.usecase.user.UpdateCurrentUser
import io.github.tuguzt.flexibleproject.domain.usecase.workspace.CreateWorkspace
import io.github.tuguzt.flexibleproject.domain.usecase.workspace.DeleteWorkspace
import io.github.tuguzt.flexibleproject.domain.usecase.workspace.FilterWorkspaces
import io.github.tuguzt.flexibleproject.domain.usecase.workspace.UpdateWorkspace

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
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
    fun provideFilterUsers(repository: UserRepository) = FilterUsers(repository)

    @Provides
    fun provideGetCurrentUser(repository: CurrentUserRepository) = GetCurrentUser(repository)

    @Provides
    fun provideFilterWorkspaces(repository: WorkspaceRepository) = FilterWorkspaces(repository)

    @Provides
    fun provideCreateWorkspace(repository: WorkspaceRepository) = CreateWorkspace(repository)

    @Provides
    fun provideUpdateWorkspace(repository: WorkspaceRepository) = UpdateWorkspace(repository)

    @Provides
    fun provideDeleteWorkspace(repository: WorkspaceRepository) = DeleteWorkspace(repository)

    @Provides
    fun provideGetSettings(repository: SettingsRepository) = GetSettings(repository)

    @Provides
    fun provideUpdateSettings(repository: SettingsRepository) = UpdateSettings(repository)

    @Provides
    fun provideFilterProject(repository: ProjectRepository) = FilterProjects(repository)

    @Provides
    fun provideCreateProject(
        projectRepository: ProjectRepository,
        workspaceRepository: WorkspaceRepository,
    ) = CreateProject(projectRepository, workspaceRepository)

    @Provides
    fun provideUpdateProject(
        projectRepository: ProjectRepository,
        workspaceRepository: WorkspaceRepository,
    ) = UpdateProject(projectRepository, workspaceRepository)

    @Provides
    fun provideDeleteProject(repository: ProjectRepository) = DeleteProject(repository)
}
