package io.github.tuguzt.flexibleproject.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.tuguzt.flexibleproject.domain.repository.user.UserRepository
import io.github.tuguzt.flexibleproject.domain.usecase.user.ReadUser

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Provides
    fun provideReadUser(repository: UserRepository) = ReadUser(repository)
}
