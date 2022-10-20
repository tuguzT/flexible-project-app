package io.github.tuguzt.flexibleproject.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.tuguzt.flexibleproject.domain.usecase.UsernameVerifier as DomainUsernameVerifier
import io.github.tuguzt.flexibleproject.data.interactor.UsernameVerifier
import io.github.tuguzt.flexibleproject.domain.usecase.PasswordVerifier as DomainPasswordVerifier
import io.github.tuguzt.flexibleproject.data.interactor.PasswordVerifier
import io.github.tuguzt.flexibleproject.domain.usecase.UserCredentialsVerifier as DomainUserCredentialsVerifier
import io.github.tuguzt.flexibleproject.data.interactor.UserCredentialsVerifier

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideUsernameVerifier(): DomainUsernameVerifier = UsernameVerifier()

    @Provides
    fun providePasswordVerifier(): DomainPasswordVerifier = PasswordVerifier()

    @Provides
    fun provideUserCredentialsVerifier(
        usernameVerifier: DomainUsernameVerifier,
        passwordVerifier: DomainPasswordVerifier,
    ): DomainUserCredentialsVerifier = UserCredentialsVerifier(usernameVerifier, passwordVerifier)
}
