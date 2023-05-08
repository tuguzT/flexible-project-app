package io.github.tuguzt.flexibleproject.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.tuguzt.flexibleproject.data.repository.user.MockUserRepository
import io.github.tuguzt.flexibleproject.domain.repository.user.UserRepository

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    fun provideUserRepository(): UserRepository = MockUserRepository()
}
